package com.app.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.app.core.data.providers.search.SearchQueryProvider
import com.app.core.data.providers.sort.SortTypeProvider
import com.app.core.data.remotemediators.LaunchpadsRemoteMediator
import com.app.core.data.util.DataConstants
import com.app.core.database.SpaceXDatabase
import com.app.core.database.model.launchpad.asExternalDetailModel
import com.app.core.database.model.launchpad.asExternalModel
import com.app.core.model.DataType
import com.app.core.model.Launchpad
import com.app.core.model.LaunchpadDetail
import com.app.core.model.sort.LaunchpadSortType
import com.app.core.network.SpaceXService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class LaunchpadsRepositoryImpl @Inject constructor(
    private val spaceXService: SpaceXService,
    private val database: SpaceXDatabase,
    private val sortTypeProvider: SortTypeProvider<LaunchpadSortType>,
    private val searchQueryProvider: SearchQueryProvider,
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
                    sortTypeProvider.getSortType()
                }
                val query = searchQueryProvider.queryMap[DataType.LAUNCHPADS.name] ?: ""
                when (sortType) {
                    LaunchpadSortType.NAME_ASC -> database.launchpadsDao().getAllAsc(query)
                    LaunchpadSortType.NAME_DESC -> database.launchpadsDao().getAllDesc(query)
                }
            }
        ).flow.map {
            it.map { launchpadResultEntity -> launchpadResultEntity.asExternalModel() }
        }
    }

    override suspend fun getLaunchpadById(id: String): LaunchpadDetail {
        return database.launchpadsDao().getItemById(id).asExternalDetailModel()
    }

    override fun getLaunchpadSortType(): Flow<LaunchpadSortType> {
        return sortTypeProvider.getSortTypeFlow()
    }

    override suspend fun saveLaunchpadSortType(sortType: LaunchpadSortType) {
        sortTypeProvider.saveSortType(sortType)
    }

    override suspend fun saveSearchQuery(query: String) {
        searchQueryProvider.queryMap[DataType.LAUNCHPADS.name] = query
    }
}