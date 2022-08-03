package com.example.spacexapp.model.remote.mappers

import com.example.spacexapp.model.local.entities.RocketEntity
import com.example.spacexapp.model.remote.responses.RocketResponse
import com.example.spacexapp.util.Mapper

class RocketResponseMapper : Mapper<RocketResponse, RocketEntity> {
    override fun map(input: RocketResponse) =
        RocketEntity(input.id, input.name, input.active, input.images)
}