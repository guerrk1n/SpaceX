package com.app.core.data.remotemediators

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.room.withTransaction
import com.app.core.common.Constants
import com.app.core.data.model.asEntity
import com.app.core.database.SpaceXDatabase
import com.app.core.database.model.RemoteKeysEntity
import com.app.core.database.model.RocketEntity
import com.app.core.network.SpaceXService
import com.app.core.network.model.Options
import com.app.core.network.model.QueryBody
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@ExperimentalPagingApi
class RocketsRemoteMediator(
    private val spaceXService: SpaceXService,
    private val database: SpaceXDatabase,
) : BaseRemoteMediator<RocketEntity>(database.remoteKeysDao()) {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(
            Constants.REMOTE_MEDIATOR_CACHE_TIMEOUT_IN_HOURS,
            TimeUnit.HOURS
        )
        var lastRocketEntity: RocketEntity? = null
        database.withTransaction {
            lastRocketEntity = database.rocketDao().getLast()
        }
        val isCacheTimeout = lastRocketEntity?.let {
            (System.currentTimeMillis() - it.createdAt) >= cacheTimeout
        } ?: true
        return if (isCacheTimeout) {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RocketEntity>,
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }


            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }
        try {
            val options = Options(page, Constants.PAGE_SIZE)
            val queryBody = QueryBody(options)
            val apiResponse = spaceXService.getRockets(queryBody)
            val endOfPaginationReached = page >= apiResponse.totalPages
            val rockets = apiResponse.rockets
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().clearRemoteKeys()
                    database.rocketDao().clearAll()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = rockets.map {
                    RemoteKeysEntity(it.id, prevKey, nextKey)
                }
                database.remoteKeysDao().insertAll(keys)
                database.rocketDao().insertAll(rockets.map { it.asEntity() })
            }
            return MediatorResult.Success(endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }
}