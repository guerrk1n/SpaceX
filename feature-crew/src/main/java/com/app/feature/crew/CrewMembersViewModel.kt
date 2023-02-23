package com.app.feature.crew

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.core.data.repository.CrewMembersRepository
import com.app.core.model.CrewMember
import com.app.core.model.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CrewMembersViewModel @Inject constructor(
    private val crewMembersRepository: CrewMembersRepository,
) : ViewModel() {

    val crewMembers: Flow<PagingData<CrewMember>> =
        crewMembersRepository.getCrewMembersStream().cachedIn(viewModelScope)

    private val _uiEffects = MutableSharedFlow<CrewMembersUiEffect>()
    val uiEffects = _uiEffects.asSharedFlow()

    val sortType: StateFlow<SortType> = crewMembersRepository.getCrewMembersSortType().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SortType.ASC
    )

    private val pendingActions = MutableSharedFlow<CrewMembersAction>()

    init {
        viewModelScope.launch {
            pendingActions.collect {
                when (it) {
                    is CrewMembersAction.ChangeSortType -> {
                        onSortTypeChanged(it.type)
                    }
                }
            }
        }
    }

    fun submitAction(action: CrewMembersAction) {
        viewModelScope.launch {
            pendingActions.emit(action)
        }
    }

    private fun onSortTypeChanged(type: SortType) {
        viewModelScope.launch {
            crewMembersRepository.saveCrewMembersSortType(type)
            submitUiEffect(CrewMembersUiEffect.ChangeSortType())
        }
    }

    private fun submitUiEffect(effect: CrewMembersUiEffect) {
        viewModelScope.launch {
            _uiEffects.emit(effect)
        }
    }
}