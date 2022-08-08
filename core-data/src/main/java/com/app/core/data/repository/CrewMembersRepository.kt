package com.app.core.data.repository

import androidx.paging.PagingData
import com.app.core.model.CrewMember
import kotlinx.coroutines.flow.Flow

interface CrewMembersRepository {

    fun getCrewMembersStream(): Flow<PagingData<CrewMember>>
}