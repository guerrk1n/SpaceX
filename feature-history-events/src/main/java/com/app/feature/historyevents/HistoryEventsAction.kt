package com.app.feature.historyevents

import com.app.core.model.SortType

sealed class HistoryEventsAction {

    class ChangeSortType(val type: SortType) : HistoryEventsAction()
}