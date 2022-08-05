package com.app.spacexapp.ui.screens.maintabs.crew

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.app.spacexapp.data.repository.CrewMembersRepository
import com.app.spacexapp.ui.screens.maintabs.crew.member.CrewMember
import com.app.spacexapp.model.local.mappers.CrewMemberEntityToCrewMemberMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CrewMembersViewModel(
    crewMembersRepository: CrewMembersRepository,
    private val mapper: CrewMemberEntityToCrewMemberMapper,
) : ViewModel() {

    val crewMembers: Flow<PagingData<CrewMember>> =
        crewMembersRepository.getCrewMembersStream()
            .map { pagingData -> pagingData.map(mapper::map) }.cachedIn(viewModelScope)
}