package com.example.spacexapp.ui.screens.maintabs.rockets.rocket

import com.example.spacexapp.data.database.model.RocketEntity
import com.example.spacexapp.data.network.RocketResponse
import com.example.spacexapp.util.Mapper

class RocketResponseMapper : Mapper<RocketResponse, RocketEntity> {
    override fun map(input: RocketResponse) =
        RocketEntity(input.id, input.name, input.active, input.images)
}