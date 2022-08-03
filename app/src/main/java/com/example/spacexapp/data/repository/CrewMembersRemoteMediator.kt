package com.example.spacexapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.spacexapp.api.SpaceXService
import com.example.spacexapp.data.database.CrewMembersDatabase
import com.example.spacexapp.data.database.model.CrewMemberEntity
import com.example.spacexapp.data.database.model.RemoteKeysEntity
import com.example.spacexapp.data.network.Options
import com.example.spacexapp.data.network.QueryBody
import com.example.spacexapp.ui.screens.maintabs.crew.member.CrewMemberResponseMapper
import com.example.spacexapp.util.Constants
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CrewMembersRemoteMediator(
    private val spaceXService: SpaceXService,
    private val crewMembersDatabase: CrewMembersDatabase,
    private val mapper: CrewMemberResponseMapper,
) : RemoteMediator<Int, CrewMemberEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CrewMemberEntity>,
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: CREW_MEMBERS_STARTING_PAGE_INDEX
            }

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
            val options = Options(page, Constants.PAGE_SIZE)
            val queryBody = QueryBody(options)
            val apiResponse = spaceXService.getCrewMembers(queryBody)
            val endOfPaginationReached = page >= apiResponse.totalPages
            val crewMembers = apiResponse.crewMembers
            crewMembersDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    crewMembersDatabase.remoteKeysDao().clearRemoteKeys()
                    crewMembersDatabase.crewMembersDao().clearItems()
                }
                val prevKey = if (page == CREW_MEMBERS_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = crewMembers.map {
                    RemoteKeysEntity(it.id, prevKey, nextKey)
                }
                crewMembersDatabase.remoteKeysDao().insertAll(keys)
                crewMembersDatabase.crewMembersDao().insertAll(crewMembers.map(mapper::map))
            }
            return MediatorResult.Success(endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CrewMemberEntity>): RemoteKeysEntity? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { crewMember ->
                crewMembersDatabase.remoteKeysDao().remoteKeysRepoId(crewMember.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, CrewMemberEntity>): RemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { crewMember ->
                crewMembersDatabase.remoteKeysDao().remoteKeysRepoId(crewMember.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, CrewMemberEntity>,
    ): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { crewMemberId ->
                crewMembersDatabase.remoteKeysDao().remoteKeysRepoId(crewMemberId)
            }
        }
    }
}

private const val CREW_MEMBERS_STARTING_PAGE_INDEX = 1