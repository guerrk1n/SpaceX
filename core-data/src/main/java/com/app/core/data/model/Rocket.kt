package com.app.core.data.model

import com.app.core.database.model.RocketEntity
import com.app.core.network.model.NetworkRocket

fun NetworkRocket.asEntity() = RocketEntity(
    id = id,
    name = name,
    active = active,
    images = images,
    firstFlight = firstFlight,
    wikipedia = wikipedia,
    description = description,
    createdAt = System.currentTimeMillis(),
)