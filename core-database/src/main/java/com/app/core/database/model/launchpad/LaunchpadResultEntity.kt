package com.app.core.database.model.launchpad

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.app.core.database.model.BaseEntity
import com.app.core.database.model.rocket.RocketEntity
import com.app.core.model.Launchpad
import com.app.core.model.LaunchpadDetail

data class LaunchpadResultEntity(
    @Embedded val launchpad: LaunchpadEntity,
    @Relation(
        parentColumn = LaunchpadEntity.FIELD_ID,
        entityColumn = LaunchpadImageEntity.FIELD_ID
    )
    val images: List<LaunchpadImageEntity>,
    @Relation(
        parentColumn = LaunchpadEntity.FIELD_ID,
        entityColumn = RocketEntity.FIELD_ID,
        associateBy = Junction(LaunchpadRocketCrossRefEntity::class)
    )
    val rockets: List<RocketEntity>,
) : BaseEntity() {
    override val id: String
        get() = launchpad.id
}

fun LaunchpadResultEntity.asExternalModel() = Launchpad(
    id = id,
    name = launchpad.name,
    fullName = launchpad.fullName,
    status = launchpad.status,
    image = images.first().image,
)

fun LaunchpadResultEntity.asExternalDetailModel() = LaunchpadDetail(
    id = id,
    name = launchpad.name,
    fullName = launchpad.fullName,
    status = launchpad.status,
    locality = launchpad.locality,
    region = launchpad.region,
    landingAttempts = launchpad.landingAttempts,
    landingSuccesses = launchpad.landingSuccesses,
    images = images.map { it.image },
    details = launchpad.details,
    rockets = rockets.map { it.id },
    timeZone = launchpad.timeZone
)