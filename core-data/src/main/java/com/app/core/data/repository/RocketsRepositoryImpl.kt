package com.app.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.app.core.data.providers.DataType
import com.app.core.data.providers.SortTypeProvider
import com.app.core.data.remotemediators.RocketsRemoteMediator
import com.app.core.data.util.DataConstants
import com.app.core.database.SpaceXDatabase
import com.app.core.database.model.asExternalDetailModel
import com.app.core.database.model.asExternalModel
import com.app.core.model.Rocket
import com.app.core.model.RocketDetail
import com.app.core.model.SortType
import com.app.core.network.SpaceXService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class RocketsRepositoryImpl @Inject constructor(
    private val spaceXService: SpaceXService,
    private val database: SpaceXDatabase,
    private val sortTypeProvider: SortTypeProvider,
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
                    sortTypeProvider.getSortType(DataType.Rockets)
                }
                when (sortType) {
                    SortType.ASC -> database.rocketDao().getAllAsc()
                    SortType.DESC -> database.rocketDao().getAllDesc()
                }
            }
        ).flow.map {
            it.map { rocketEntity -> rocketEntity.asExternalModel() }
        }
    }

    override suspend fun getRocketById(id: String): RocketDetail {
        return database.rocketDao().getItemById(id).asExternalDetailModel()
    }

    override fun getRocketSortType(): Flow<SortType> {
        return sortTypeProvider.getSortTypeFlow(DataType.Rockets)
    }

    override suspend fun saveRocketSortType(sortType: SortType) {
        sortTypeProvider.saveSortType(sortType, DataType.Rockets)
    }
}