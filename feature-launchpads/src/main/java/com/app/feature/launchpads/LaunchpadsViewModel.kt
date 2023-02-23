package com.app.feature.launchpads

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.core.data.repository.LaunchpadsRepository
import com.app.core.model.Launchpad
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
class LaunchpadsViewModel @Inject constructor(
    private val launchpadsRepository: LaunchpadsRepository,
) : ViewModel() {

    val launchpads: Flow<PagingData<Launchpad>> =
        launchpadsRepository.getLaunchpadsStream().cachedIn(viewModelScope)

    private val _uiEffects = MutableSharedFlow<LaunchpadsUiEffect>()
    val uiEffects = _uiEffects.asSharedFlow()

    val sortType: StateFlow<SortType> = launchpadsRepository.getLaunchpadSortType().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SortType.NAME_ASC
    )

    private val pendingActions = MutableSharedFlow<LaunchpadsAction>()

    init {
        viewModelScope.launch {
            pendingActions.collect {
                when (it) {
                    is LaunchpadsAction.ChangeSortType -> {
                        onSortTypeChanged(it.type)
                    }
                }
            }
        }
    }

    fun submitAction(action: LaunchpadsAction) {
        viewModelScope.launch {
            pendingActions.emit(action)
        }
    }

    private fun onSortTypeChanged(type: SortType) {
        viewModelScope.launch {
            launchpadsRepository.saveLaunchpadSortType(type)
            submitUiEffect(LaunchpadsUiEffect.ChangeSortType())
        }
    }

    private fun submitUiEffect(effect: LaunchpadsUiEffect) {
        viewModelScope.launch {
            _uiEffects.emit(effect)
        }
    }
}