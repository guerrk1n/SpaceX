package com.app.spacexapp.model.remote.mappers

import com.app.spacexapp.model.local.entities.RocketEntity
import com.app.spacexapp.model.remote.responses.RocketResponse
import com.app.spacexapp.util.Mapper

class RocketResponseToRocketEntityMapper : Mapper<RocketResponse, RocketEntity> {
    override fun map(input: RocketResponse) =
        RocketEntity(
            input.id,
            input.name,
            input.active,
            input.images,
            input.firstFlight,
            input.wikipedia,
            input.description,
            System.currentTimeMillis(),
        )
}