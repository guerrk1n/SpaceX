package com.example.spacexapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.spacexapp.data.local.CrewMembersDatabase
import com.example.spacexapp.data.paging.CrewMembersRemoteMediator
import com.example.spacexapp.data.remote.SpaceXService
import com.example.spacexapp.model.local.entities.CrewMemberEntity
import com.example.spacexapp.model.remote.mappers.CrewMemberResponseMapper
import com.example.spacexapp.util.Constants
import kotlinx.coroutines.flow.Flow

class CrewMembersRepository(
    private val spaceXService: SpaceXService,
    private val database: CrewMembersDatabase,
    private val mapper: CrewMemberResponseMapper,
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getCrewMembersStream(): Flow<PagingData<CrewMemberEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = CrewMembersRemoteMediator(spaceXService, database, mapper),
            pagingSourceFactory = { database.crewMembersDao().getAllItems() }
        ).flow
    }
}