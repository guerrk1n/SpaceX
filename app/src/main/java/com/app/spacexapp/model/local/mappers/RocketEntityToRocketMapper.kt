package com.app.spacexapp.model.local.mappers

import com.app.spacexapp.model.local.entities.RocketEntity
import com.app.spacexapp.ui.screens.maintabs.rockets.rocket.Rocket
import com.app.spacexapp.util.Mapper

class RocketEntityToRocketMapper : Mapper<RocketEntity, Rocket> {
    override fun map(input: RocketEntity) =
        Rocket(
            input.id,
            input.name,
            input.active,
            input.images,
        )
}