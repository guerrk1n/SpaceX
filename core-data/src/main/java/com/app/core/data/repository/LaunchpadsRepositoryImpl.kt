package com.app.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.app.core.data.providers.DataType
import com.app.core.data.providers.SortTypeProvider
import com.app.core.data.remotemediators.LaunchpadsRemoteMediator
import com.app.core.data.util.DataConstants
import com.app.core.database.SpaceXDatabase
import com.app.core.database.model.asExternalDetailModel
import com.app.core.database.model.asExternalModel
import com.app.core.model.Launchpad
import com.app.core.model.LaunchpadDetail
import com.app.core.model.SortType
import com.app.core.network.SpaceXService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class LaunchpadsRepositoryImpl @Inject constructor(
    private val spaceXService: SpaceXService,
    private val database: SpaceXDatabase,
    private val sortTypeProvider: SortTypeProvider,
) : LaunchpadsRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getLaunchpadsStream(): Flow<PagingData<Launchpad>> {
        return Pager(
            config = PagingConfig(
                pageSize = DataConstants.PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = LaunchpadsRemoteMediator(spaceXService, database, sortTypeProvider),
            pagingSourceFactory = {
                val sortType = runBlocking { // todo
                    sortTypeProvider.getSortType(DataType.Launchpads)
                }
                when (sortType) {
                    SortType.ASC -> database.launchpadsDao().getAllAsc()
                    SortType.DESC -> database.launchpadsDao().getAllDesc()
                }
            }
        ).flow.map {
            it.map { launchpadEntity -> launchpadEntity.asExternalModel() }
        }
    }

    override suspend fun getLaunchpadById(id: String): LaunchpadDetail {
        return database.launchpadsDao().getItemById(id).asExternalDetailModel()
    }

    override fun getLaunchpadSortType(): Flow<SortType> {
        return sortTypeProvider.getSortTypeFlow(DataType.Launchpads)
    }

    override suspend fun saveLaunchpadSortType(sortType: SortType) {
        sortTypeProvider.saveSortType(sortType, DataType.Launchpads)
    }
}