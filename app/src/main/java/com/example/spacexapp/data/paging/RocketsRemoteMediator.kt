package com.example.spacexapp.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.spacexapp.data.local.RocketDatabase
import com.example.spacexapp.data.remote.SpaceXService
import com.example.spacexapp.model.local.entities.RemoteKeysEntity
import com.example.spacexapp.model.local.entities.RocketEntity
import com.example.spacexapp.model.remote.Options
import com.example.spacexapp.model.remote.QueryBody
import com.example.spacexapp.model.remote.mappers.RocketResponseMapper
import com.example.spacexapp.util.Constants
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class RocketsRemoteMediator(
    private val spaceXService: SpaceXService,
    private val rocketDatabase: RocketDatabase,
    private val mapper: RocketResponseMapper,
) : RemoteMediator<Int, RocketEntity>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(24, TimeUnit.HOURS)
        var lastRocketEntity: RocketEntity? = null
        rocketDatabase.withTransaction {
            lastRocketEntity = rocketDatabase.rocketDao().getLast()
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
                remoteKeys?.nextKey?.minus(1) ?: ROCKETS_STARTING_PAGE_INDEX
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
            rocketDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    rocketDatabase.remoteKeysDao().clearRemoteKeys()
                    rocketDatabase.rocketDao().clearAll()
                }
                val prevKey = if (page == ROCKETS_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = rockets.map {
                    RemoteKeysEntity(it.id, prevKey, nextKey)
                }
                rocketDatabase.remoteKeysDao().insertAll(keys)
                rocketDatabase.rocketDao().insertAll(rockets.map(mapper::map))
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

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, RocketEntity>): RemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { rocket ->
                rocketDatabase.remoteKeysDao().remoteKeysRepoId(rocket.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, RocketEntity>): RemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { rocket ->
                rocketDatabase.remoteKeysDao().remoteKeysRepoId(rocket.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, RocketEntity>,
    ): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { rocketId ->
                rocketDatabase.remoteKeysDao().remoteKeysRepoId(rocketId)
            }
        }
    }
}

private const val ROCKETS_STARTING_PAGE_INDEX = 1