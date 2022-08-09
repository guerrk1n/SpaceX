package com.app.core.data.repository

import androidx.paging.*
import com.app.core.data.remotemediators.CrewMembersRemoteMediator
import com.app.core.data.util.DataConstants
import com.app.core.database.SpaceXDatabase
import com.app.core.database.model.asExternalModel
import com.app.core.model.CrewMember
import com.app.core.network.SpaceXService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CrewMembersRepositoryImpl @Inject constructor(
    private val spaceXService: SpaceXService,
    private val database: SpaceXDatabase,
) : CrewMembersRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getCrewMembersStream(): Flow<PagingData<CrewMember>> {
        return Pager(
            config = PagingConfig(
                pageSize = DataConstants.PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = CrewMembersRemoteMediator(spaceXService, database),
            pagingSourceFactory = { database.crewMembersDao().getAll() }
        ).flow.map {
            it.map { crewMemberEntity -> crewMemberEntity.asExternalModel() }
        }
    }
}