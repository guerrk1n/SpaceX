package com.app.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.core.model.Rocket
import com.app.core.model.RocketDetail

@Entity(tableName = "rocket_dbo")
data class RocketEntity(
    @PrimaryKey
    override val id: String,
    val name: String,
    val active: Boolean,
    val images: List<String>,
    val firstFlight: String,
    val wikipedia: String,
    val description: String,
    val createdAt: Long,
): BaseEntity()

fun RocketEntity.asExternalModel() = Rocket(
    id = id,
    name = name,
    active = active,
    images = images,
)

fun RocketEntity.asExternalDetailModel() = RocketDetail(
    id = id,
    name = name,
    active = active,
    images = images,
    firstFlight = firstFlight,
    wikipedia = wikipedia,
    description = description,
)