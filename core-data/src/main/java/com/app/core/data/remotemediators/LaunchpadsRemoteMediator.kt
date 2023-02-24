package com.app.core.data.remotemediators

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.room.withTransaction
import com.app.core.data.model.asEntity
import com.app.core.data.model.asLaunchpadImageEntity
import com.app.core.data.providers.DataType
import com.app.core.data.providers.SortTypeProvider
import com.app.core.data.util.DataConstants
import com.app.core.database.SpaceXDatabase
import com.app.core.database.model.RemoteKeysEntity
import com.app.core.database.model.launchpad.LaunchpadWithImagesEntity
import com.app.core.network.SpaceXService
import com.app.core.network.model.NetworkLaunchpad
import com.app.core.network.model.Options
import com.app.core.network.model.QueryBody
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

@ExperimentalPagingApi
class LaunchpadsRemoteMediator(
    private val spaceXService: SpaceXService,
    private val database: SpaceXDatabase,
    private val sortTypeProvider: SortTypeProvider,
) : BaseRemoteMediator<LaunchpadWithImagesEntity>(database.remoteKeysDao()) {

    override suspend fun initialize(): InitializeAction {
        var createdTime: Long? = null
        database.withTransaction {
            createdTime = database.launchpadsDao().getLastCreatedAtTime()
        }
        return getInitializeAction(createdTime)
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LaunchpadWithImagesEntity>,
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
            val sortType = sortTypeProvider.getSortType(DataType.Launchpads)
            val sortParameter = mapOf(NetworkLaunchpad.FIELD_NAME to sortType.value)
            val options = Options(page, DataConstants.PAGE_SIZE, sortParameter)
            val queryBody = QueryBody(options)
            val apiResponse = spaceXService.getLaunchpads(queryBody)
            val endOfPaginationReached = page >= apiResponse.totalPages
            val launchpads = apiResponse.launchpads
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().clearRemoteKeys()
                    database.launchpadsDao().clearAll()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = launchpads.map {
                    RemoteKeysEntity(it.id, prevKey, nextKey)
                }
                database.remoteKeysDao().insertAll(keys)
                val launchpadsWithImages = launchpads.map { launchpad ->
                    val launchpadEntity = launchpad.asEntity()
                    val launchpadImageEntities = launchpad.asLaunchpadImageEntity()
                    launchpadEntity to launchpadImageEntities
                }
                launchpadsWithImages.forEach { (launchpadEntity, launchpadImageEntities) ->
                    database.launchpadsDao().insertLaunchpadWithImages(launchpadEntity, launchpadImageEntities)
                }
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
}