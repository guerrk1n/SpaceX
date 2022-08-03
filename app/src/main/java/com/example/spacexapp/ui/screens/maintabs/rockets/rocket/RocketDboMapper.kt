package com.example.spacexapp.ui.screens.maintabs.rockets.rocket

import com.example.spacexapp.data.database.model.RocketEntity
import com.example.spacexapp.util.Mapper

class RocketDboMapper : Mapper<RocketEntity, Rocket> {
    override fun map(input: RocketEntity) =
        Rocket(input.name, input.active, input.images, input.id)
}