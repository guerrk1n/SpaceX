package com.app.feature.historyevents

import com.app.core.model.sort.HistoryEventSortType

sealed class HistoryEventsAction {

    class ChangeSortType(val type: HistoryEventSortType) : HistoryEventsAction()

    class ChangeQuery(val query: String) : HistoryEventsAction()
}