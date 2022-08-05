package com.app.spacexapp.model.local.mappers

import com.app.spacexapp.model.local.entities.RocketEntity
import com.app.spacexapp.ui.screens.details.rocket.RocketDetail
import com.app.spacexapp.ui.screens.maintabs.rockets.rocket.Rocket
import com.app.spacexapp.util.Mapper

class RocketEntityToRocketDetailMapper : Mapper<RocketEntity, RocketDetail> {
    override fun map(input: RocketEntity) =
        RocketDetail(
            input.id,
            input.name,
            input.active,
            input.images,
            input.firstFlight,
            input.wikipedia,
            input.description,
        )
}