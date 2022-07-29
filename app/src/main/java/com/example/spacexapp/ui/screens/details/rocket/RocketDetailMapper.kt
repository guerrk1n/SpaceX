package com.example.spacexapp.ui.screens.details.rocket

import com.example.spacexapp.data.rockets.RocketDetailsResponse
import com.example.spacexapp.util.Mapper

class RocketDetailMapper : Mapper<RocketDetailsResponse, RocketDetail> {
    override fun map(input: RocketDetailsResponse) = RocketDetail(
        input.name,
        input.active,
        input.images,
        input.id,
        input.firstFlight,
        input.wikipedia,
        input.description,
    )
}