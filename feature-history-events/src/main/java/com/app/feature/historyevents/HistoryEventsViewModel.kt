package com.app.feature.historyevents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.core.data.repository.HistoryEventsRepository
import com.app.core.model.HistoryEvent
import com.app.core.model.sort.HistoryEventSortType
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
class HistoryEventsViewModel @Inject constructor(
    private val historyEventsRepository: HistoryEventsRepository,
) : ViewModel() {

    val historyEvents: Flow<PagingData<HistoryEvent>> =
        historyEventsRepository.getHistoryEventsStream().cachedIn(viewModelScope)

    private val _uiEffects = MutableSharedFlow<HistoryEventsUiEffect>()
    val uiEffects = _uiEffects.asSharedFlow()

    val sortType: StateFlow<HistoryEventSortType> = historyEventsRepository.getHistoryEventSortType().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = HistoryEventSortType.NAME_ASC
    )

    private val pendingActions = MutableSharedFlow<HistoryEventsAction>()

    init {
        viewModelScope.launch {
            pendingActions.collect {
                when (it) {
                    is HistoryEventsAction.ChangeSortType -> onSortTypeChanged(it.type)
                    is HistoryEventsAction.ChangeQuery -> onQueryChanged(it.query)
                }
            }
        }
    }

    fun submitAction(action: HistoryEventsAction) {
        viewModelScope.launch {
            pendingActions.emit(action)
        }
    }

    private fun onQueryChanged(query: String) {
        viewModelScope.launch {
            historyEventsRepository.saveSearchQuery(query)
            submitUiEffect(HistoryEventsUiEffect.QueryChanged())
        }
    }

    private fun onSortTypeChanged(type: HistoryEventSortType) {
        viewModelScope.launch {
            historyEventsRepository.saveHistoryEventSortType(type)
            submitUiEffect(HistoryEventsUiEffect.ChangeSortType())
        }
    }

    private fun submitUiEffect(effect: HistoryEventsUiEffect) {
        viewModelScope.launch {
            _uiEffects.emit(effect)
        }
    }

}