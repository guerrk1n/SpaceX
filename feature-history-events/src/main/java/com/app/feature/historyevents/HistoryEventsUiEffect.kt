package com.app.feature.historyevents

sealed class HistoryEventsUiEffect {
    class ChangeSortType: HistoryEventsUiEffect()

    class QueryChanged: HistoryEventsUiEffect()
}