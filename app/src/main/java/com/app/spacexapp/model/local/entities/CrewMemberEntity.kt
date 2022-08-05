package com.app.spacexapp.model.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crew_member_dbo")
data class CrewMemberEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val agency: String,
    val image: String,
    val wikipedia: String,
    val status: String,
    val createdAt: Long,
)