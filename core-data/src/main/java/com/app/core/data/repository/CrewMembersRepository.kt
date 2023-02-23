package com.app.core.data.repository

import androidx.paging.PagingData
import com.app.core.model.CrewMember
import com.app.core.model.SortType
import kotlinx.coroutines.flow.Flow

interface CrewMembersRepository {

    fun getCrewMembersStream(): Flow<PagingData<CrewMember>>

    fun getCrewMembersSortType(): Flow<SortType>

    suspend fun saveCrewMembersSortType(sortType: SortType)
}