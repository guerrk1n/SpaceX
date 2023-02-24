package com.app.core.data.model

import com.app.core.database.model.launchpad.LaunchpadEntity
import com.app.core.database.model.launchpad.LaunchpadImageEntity
import com.app.core.database.model.launchpad.LaunchpadRocketCrossRefEntity
import com.app.core.network.model.NetworkLaunchpad

fun NetworkLaunchpad.asEntity() = LaunchpadEntity(
    id = id,
    name = name,
    fullName = fullName,
    status = status,
    locality = locality,
    region = region,
    landingAttempts = launchAttempts,
    landingSuccesses = launchSuccesses,
    details = details,
    timeZone = timeZone,
    createdAt = System.currentTimeMillis(),
)

fun NetworkLaunchpad.asLaunchpadImageEntity(): List<LaunchpadImageEntity> = images.large.map {
    LaunchpadImageEntity(id, it)
}

fun NetworkLaunchpad.asLaunchpadRocketCrossRefsEntity(): List<LaunchpadRocketCrossRefEntity> = rockets.map {
    LaunchpadRocketCrossRefEntity(id, it)
}