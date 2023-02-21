package com.app.core.data.model

import com.app.core.database.model.LaunchpadEntity
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
    images = images.large,
    details = details,
    launches = launches,
    rockets = rockets,
    timeZone = timeZone,
    createdAt = System.currentTimeMillis(),
)