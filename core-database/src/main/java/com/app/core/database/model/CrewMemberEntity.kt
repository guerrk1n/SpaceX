package com.app.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.core.model.CrewMember

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

fun CrewMemberEntity.asExternalModel() = CrewMember(
    id = id,
    name = name,
    agency = agency,
    image = image,
    wikipedia = wikipedia,
    status = status,
)