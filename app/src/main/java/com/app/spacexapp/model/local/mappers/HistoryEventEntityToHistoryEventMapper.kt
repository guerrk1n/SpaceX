package com.app.spacexapp.model.local.mappers

import com.app.spacexapp.model.local.entities.HistoryEventEntity
import com.app.spacexapp.ui.screens.maintabs.historyevents.historyevent.HistoryEvent
import com.app.spacexapp.util.Mapper

class HistoryEventEntityToHistoryEventMapper : Mapper<HistoryEventEntity, HistoryEvent> {
    override fun map(input: HistoryEventEntity) = HistoryEvent(
        input.id,
        input.link,
        input.title,
        input.date,
        input.details,
    )
}