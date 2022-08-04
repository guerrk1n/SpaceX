package com.example.spacexapp.model.local.mappers

import com.example.spacexapp.model.local.entities.RocketEntity
import com.example.spacexapp.ui.screens.details.rocket.RocketDetail
import com.example.spacexapp.ui.screens.maintabs.rockets.rocket.Rocket
import com.example.spacexapp.util.Mapper

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