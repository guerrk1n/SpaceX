package com.example.spacexapp.ui.screens.maintabs.historyevents.historyevent

import com.example.spacexapp.data.database.model.HistoryEventEntity
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