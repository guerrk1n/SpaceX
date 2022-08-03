package com.example.spacexapp.model.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rocket_dbo")
data class RocketEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val active: Boolean,
    val images: List<String>,
)