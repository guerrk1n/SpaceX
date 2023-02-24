package com.app.core.database.model.rocket

import androidx.room.Embedded
import androidx.room.Relation
import com.app.core.database.model.BaseEntity
import com.app.core.model.Rocket
import com.app.core.model.RocketDetail

data class RocketResultEntity(
    @Embedded val rocket: RocketEntity,
    @Relation(
        parentColumn = RocketEntity.FIELD_ID,
        entityColumn = RocketImageEntity.FIELD_ID
    )
    val images: List<RocketImageEntity>,
) : BaseEntity() {
    override val id: String
        get() = rocket.id
}

fun RocketResultEntity.asExternalModel() = Rocket(
    id = rocket.id,
    name = rocket.name,
    active = rocket.active,
    images = images.map { it.image },
)

fun RocketResultEntity.asExternalDetailModel() = RocketDetail(
    id = rocket.id,
    name = rocket.name,
    active = rocket.active,
    images = images.map { it.image },
    firstFlight = rocket.firstFlight,
    wikipedia = rocket.wikipedia,
    description = rocket.description,
)