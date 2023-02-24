package com.app.core.data.model

import com.app.core.database.model.rocket.RocketEntity
import com.app.core.database.model.rocket.RocketImageEntity
import com.app.core.network.model.NetworkRocket

fun NetworkRocket.asEntity() = RocketEntity(
    id = id,
    name = name,
    active = active,
    firstFlight = firstFlight,
    wikipedia = wikipedia,
    description = description,
    createdAt = System.currentTimeMillis(),
)

fun NetworkRocket.asRocketImageEntity(): List<RocketImageEntity> = images.map {
    RocketImageEntity(id, it)
}