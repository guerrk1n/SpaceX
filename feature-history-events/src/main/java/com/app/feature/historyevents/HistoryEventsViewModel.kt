package com.app.feature.historyevents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.core.data.repository.HistoryEventsRepository
import com.app.core.model.HistoryEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HistoryEventsViewModel @Inject constructor(
    historyEventsRepository: HistoryEventsRepository,
) : ViewModel() {

    val historyEvents: Flow<PagingData<HistoryEvent>> =
        historyEventsRepository.getHistoryEventsStream().cachedIn(viewModelScope)

}