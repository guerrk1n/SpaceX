package com.app.feature.crew

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.app.core.data.repository.CrewMembersRepository
import com.app.core.model.CrewMember
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CrewMembersViewModel @Inject constructor(
    crewMembersRepository: CrewMembersRepository,
) : ViewModel() {

    val crewMembers: Flow<PagingData<CrewMember>> =
        crewMembersRepository.getCrewMembersStream()
}