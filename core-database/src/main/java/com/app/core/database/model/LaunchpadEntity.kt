package com.app.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.core.model.Launchpad
import com.app.core.model.LaunchpadDetail

@Entity(tableName = "launchpad_dbo")
data class LaunchpadEntity(
    @PrimaryKey
    override val id: String,
    val name: String,
    val fullName: String,
    val status: String,
    val locality: String,
    val region: String,
    val landingAttempts: Int,
    val landingSuccesses: Int,
    val images: List<String>,
    val details: String,
    val launches: List<String>,
    val rockets: List<String>,
    val timeZone: String,
    val createdAt: Long,
): BaseEntity()

fun LaunchpadEntity.asExternalModel() = Launchpad(
    id = id,
    name = name,
    fullName = fullName,
    status = status,
    image = images.first(),
)

fun LaunchpadEntity.asExternalDetailModel() = LaunchpadDetail(
    id = id,
    name = name,
    fullName = fullName,
    status = status,
    locality = locality,
    region = region,
    landingAttempts = landingAttempts,
    landingSuccesses = landingSuccesses,
    images = images,
    details = details,
    launches = launches,
    rockets = rockets,
    timeZone = timeZone
)