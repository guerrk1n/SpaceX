package com.app.spacexapp.model.remote.mappers

import com.app.spacexapp.model.local.entities.HistoryEventEntity
import com.app.spacexapp.model.remote.responses.HistoryEventResponse
import com.app.spacexapp.util.Constants
import com.app.spacexapp.util.Mapper

class HistoryEventResponseToHistoryEventEntityMapper : Mapper<HistoryEventResponse, HistoryEventEntity> {
    override fun map(input: HistoryEventResponse) = HistoryEventEntity(
        input.id,
        input.links.article,
        input.title,
        input.date * Constants.MILLISECONDS_IN_SECONDS,
        input.details,
        System.currentTimeMillis()
    )
}