package com.app.spacexapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.app.spacexapp.data.local.CrewMembersDatabase
import com.app.spacexapp.data.paging.CrewMembersRemoteMediator
import com.app.spacexapp.data.remote.SpaceXService
import com.app.spacexapp.model.local.entities.CrewMemberEntity
import com.app.spacexapp.model.remote.mappers.CrewMemberResponseToCrewMemberEntityMapper
import com.app.spacexapp.util.Constants
import kotlinx.coroutines.flow.Flow

class CrewMembersRepository(
    private val spaceXService: SpaceXService,
    private val database: CrewMembersDatabase,
    private val mapper: CrewMemberResponseToCrewMemberEntityMapper,
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getCrewMembersStream(): Flow<PagingData<CrewMemberEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = CrewMembersRemoteMediator(spaceXService, database, mapper),
            pagingSourceFactory = { database.crewMembersDao().getAll() }
        ).flow
    }
}