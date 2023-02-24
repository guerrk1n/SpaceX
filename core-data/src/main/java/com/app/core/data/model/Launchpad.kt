package com.app.core.data.model

import com.app.core.database.model.launchpad.LaunchpadEntity
import com.app.core.database.model.launchpad.LaunchpadImageEntity
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
    rockets = rockets,
    timeZone = timeZone,
    createdAt = System.currentTimeMillis(),
)

fun NetworkLaunchpad.asLaunchpadImageEntity(): List<LaunchpadImageEntity> = images.large.map {
    LaunchpadImageEntity(id, it)
}