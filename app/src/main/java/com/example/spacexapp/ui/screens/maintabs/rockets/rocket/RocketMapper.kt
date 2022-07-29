package com.example.spacexapp.ui.screens.maintabs.rockets.rocket

import com.example.spacexapp.data.rockets.RocketResponse
import com.example.spacexapp.util.Mapper

class RocketMapper : Mapper<RocketResponse, Rocket> {
    override fun map(input: RocketResponse) =
        Rocket(input.name, input.active, input.images, input.id)
}