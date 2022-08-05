package com.example.spacexapp.model.local.mappers

import com.example.spacexapp.model.local.entities.HistoryEventEntity
import com.example.spacexapp.ui.screens.maintabs.historyevents.historyevent.HistoryEvent
import com.example.spacexapp.util.Mapper

class HistoryEventEntityToHistoryEventMapper : Mapper<HistoryEventEntity, HistoryEvent> {
    override fun map(input: HistoryEventEntity) = HistoryEvent(
        input.id,
        input.link,
        input.title,
        input.date,
        input.details,
    )
}