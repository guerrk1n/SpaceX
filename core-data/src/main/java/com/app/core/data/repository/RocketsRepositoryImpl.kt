package com.app.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.app.core.data.providers.search.SearchQueryProvider
import com.app.core.data.providers.sort.SortTypeProvider
import com.app.core.data.remotemediators.RocketsRemoteMediator
import com.app.core.data.util.DataConstants
import com.app.core.database.SpaceXDatabase
import com.app.core.database.model.rocket.asExternalDetailModel
import com.app.core.database.model.rocket.asExternalModel
import com.app.core.model.Rocket
import com.app.core.model.RocketDetail
import com.app.core.model.sort.RocketSortType
import com.app.core.network.SpaceXService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class RocketsRepositoryImpl @Inject constructor(
    private val spaceXService: SpaceXService,
    private val database: SpaceXDatabase,
    private val sortTypeProvider: SortTypeProvider<RocketSortType>,
    private val searchQueryProvider: SearchQueryProvider,
) : RocketsRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getRocketsStream(): Flow<PagingData<Rocket>> {
        return Pager(
            config = PagingConfig(
                pageSize = DataConstants.PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = RocketsRemoteMediator(spaceXService, database, sortTypeProvider),
            pagingSourceFactory = {
                val sortType = runBlocking { // todo
                    sortTypeProvider.getSortType()
                }
                val query = searchQueryProvider.query
                when (sortType) {
                    RocketSortType.NAME_ASC -> database.rocketDao().getAllAsc(query)
                    RocketSortType.NAME_DESC -> database.rocketDao().getAllDesc(query)
                }
            }
        ).flow.map {
            it.map { rocketResultEntity -> rocketResultEntity.asExternalModel() }
        }
    }

    override suspend fun getRocketById(id: String): RocketDetail {
        return database.rocketDao().getItemById(id).asExternalDetailModel()
    }

    override fun getRocketSortType(): Flow<RocketSortType> {
        return sortTypeProvider.getSortTypeFlow()
    }

    override suspend fun saveRocketSortType(sortType: RocketSortType) {
        sortTypeProvider.saveSortType(sortType)
    }

    override suspend fun saveSearchQuery(query: String) {
        searchQueryProvider.query = query
    }
}