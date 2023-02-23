package com.app.core.data.repository

import androidx.paging.*
import com.app.core.data.providers.DataType
import com.app.core.data.providers.SortTypeProvider
import com.app.core.data.remotemediators.CrewMembersRemoteMediator
import com.app.core.data.util.DataConstants
import com.app.core.database.SpaceXDatabase
import com.app.core.database.model.asExternalModel
import com.app.core.model.CrewMember
import com.app.core.model.SortType
import com.app.core.network.SpaceXService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class CrewMembersRepositoryImpl @Inject constructor(
    private val spaceXService: SpaceXService,
    private val database: SpaceXDatabase,
    private val sortTypeProvider: SortTypeProvider,
) : CrewMembersRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getCrewMembersStream(): Flow<PagingData<CrewMember>> {
        return Pager(
            config = PagingConfig(
                pageSize = DataConstants.PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = CrewMembersRemoteMediator(spaceXService, database, sortTypeProvider),
            pagingSourceFactory = {
                val sortType = runBlocking { // todo
                    sortTypeProvider.getSortType(DataType.CrewMembers)
                }
                when (sortType) {
                    SortType.NAME_ASC -> database.crewMembersDao().getAllAsc()
                    SortType.NAME_DESC -> database.crewMembersDao().getAllDesc()
                }
            }
        ).flow.map {
            it.map { crewMemberEntity -> crewMemberEntity.asExternalModel() }
        }
    }

    override fun getCrewMembersSortType(): Flow<SortType> {
        return sortTypeProvider.getSortTypeFlow(DataType.CrewMembers)
    }

    override suspend fun saveCrewMembersSortType(sortType: SortType) {
        sortTypeProvider.saveSortType(sortType, DataType.CrewMembers)
    }
}