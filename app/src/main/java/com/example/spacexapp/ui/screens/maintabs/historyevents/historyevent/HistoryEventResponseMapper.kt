package com.example.spacexapp.ui.screens.maintabs.historyevents.historyevent

import com.example.spacexapp.data.database.model.HistoryEventEntity
import com.example.spacexapp.data.network.HistoryEventResponse
import com.example.spacexapp.util.Constants
import com.example.spacexapp.util.Mapper

class HistoryEventResponseMapper : Mapper<HistoryEventResponse, HistoryEventEntity> {
    override fun map(input: HistoryEventResponse) = HistoryEventEntity(
        input.id,
        input.links.article,
        input.title,
        input.date * Constants.MILLISECONDS_IN_SECONDS,
        input.details,
    )
}