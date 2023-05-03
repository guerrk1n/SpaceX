package com.app.core.data.repository

import androidx.paging.PagingData
import com.app.core.model.CrewMember
import com.app.core.model.sort.CrewMemberSortType
import kotlinx.coroutines.flow.Flow

interface CrewMembersRepository {

    fun getCrewMembersStream(): Flow<PagingData<CrewMember>>

    fun getCrewMembersSortType(): Flow<CrewMemberSortType>

    suspend fun saveCrewMembersSortType(sortType: CrewMemberSortType)

    suspend fun saveSearchQuery(query: String)
}