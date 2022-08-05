package com.example.spacexapp.model.remote.mappers

import com.example.spacexapp.model.local.entities.HistoryEventEntity
import com.example.spacexapp.model.remote.responses.HistoryEventResponse
import com.example.spacexapp.util.Constants
import com.example.spacexapp.util.Mapper

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