package com.example.spacexapp.model.local.mappers

import com.example.spacexapp.model.local.entities.HistoryEventEntity
import com.example.spacexapp.ui.screens.maintabs.historyevents.historyevent.HistoryEvent
import com.example.spacexapp.util.Constants
import com.example.spacexapp.util.Mapper

class HistoryEventEntityMapper : Mapper<HistoryEventEntity, HistoryEvent> {
    override fun map(input: HistoryEventEntity) = HistoryEvent(
        input.link,
        input.title,
        input.date * Constants.MILLISECONDS_IN_SECONDS,
        input.details,
        input.id,
    )
}