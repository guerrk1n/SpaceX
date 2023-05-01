package com.app.core.data.remotemediators

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.room.withTransaction
import com.app.core.data.model.asEntity
import com.app.core.data.model.asRocketImageEntity
import com.app.core.data.providers.SortTypeProvider
import com.app.core.data.util.DataConstants
import com.app.core.database.SpaceXDatabase
import com.app.core.database.model.RemoteKeysEntity
import com.app.core.database.model.rocket.RocketEntity
import com.app.core.database.model.rocket.RocketImageEntity
import com.app.core.database.model.rocket.RocketResultEntity
import com.app.core.model.sort.RocketSortType
import com.app.core.network.SpaceXService
import com.app.core.network.model.NetworkRocket
import com.app.core.network.model.Options
import com.app.core.network.model.QueryBody
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

@ExperimentalPagingApi
class RocketsRemoteMediator(
    private val spaceXService: SpaceXService,
    private val database: SpaceXDatabase,
    private val sortTypeProvider: SortTypeProvider<RocketSortType>,
) : BaseRemoteMediator<RocketResultEntity>(database.remoteKeysDao()) {

    override suspend fun initialize(): InitializeAction {
        var createdTime: Long? = null
        database.withTransaction {
            createdTime = database.rocketDao().getLastCreatedAtTime()
        }
        return getInitializeAction(createdTime)
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RocketResultEntity>,
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> getPageForRefreshLoadType(state)

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
            val sortType = sortTypeProvider.getSortType()
            val sortParameter = mapOf(NetworkRocket.FIELD_NAME to sortType.value)
            val options = Options(page, DataConstants.PAGE_SIZE, sortParameter)
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
                insertData(rockets)
            }
            return MediatorResult.Success(endOfPaginationReached)
        } catch (exception: IOException) {
            Timber.e("load exception $exception")
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            Timber.e("load exception $exception")
            return MediatorResult.Error(exception)
        } catch (exception: Exception) {
            Timber.e("load exception $exception")
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun insertData(rockets: List<NetworkRocket>) {
        val rocketsWithImages = rockets.map { rocket ->
            val rocketEntity = rocket.asEntity()
            val rocketImageEntities = rocket.asRocketImageEntity()
            RocketInsert(rocketEntity, rocketImageEntities)
        }
        rocketsWithImages.forEach { (rocketEntity, rocketImageEntities) ->
            database.rocketDao().insertRocketWithImages(rocketEntity, rocketImageEntities)
        }
    }
}

private data class RocketInsert(
    val rocketEntity: RocketEntity,
    val rocketImageEntities: List<RocketImageEntity>,
)