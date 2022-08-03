package com.example.spacexapp.model.local.mappers

import com.example.spacexapp.model.local.entities.RocketEntity
import com.example.spacexapp.ui.screens.maintabs.rockets.rocket.Rocket
import com.example.spacexapp.util.Mapper

class RocketEntityMapper : Mapper<RocketEntity, Rocket> {
    override fun map(input: RocketEntity) =
        Rocket(input.name, input.active, input.images, input.id)
}