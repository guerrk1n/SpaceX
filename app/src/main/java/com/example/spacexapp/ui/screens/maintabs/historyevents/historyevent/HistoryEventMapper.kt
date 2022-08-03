package com.example.spacexapp.ui.screens.maintabs.historyevents.historyevent

import com.example.spacexapp.data.network.HistoryEventResponse
import com.example.spacexapp.util.Constants
import com.example.spacexapp.util.Mapper

class HistoryEventMapper : Mapper<HistoryEventResponse, HistoryEvent> {
    override fun map(input: HistoryEventResponse) = HistoryEvent(
        link = input.links.article,
        title = input.title,
        date = input.date * Constants.MILLISECONDS_IN_SECONDS,
        details = input.details,
    )
}