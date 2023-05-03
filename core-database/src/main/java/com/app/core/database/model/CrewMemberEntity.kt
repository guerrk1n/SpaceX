package com.app.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.core.model.CrewMember

@Entity(tableName = CrewMemberEntity.TABLE_NAME)
data class CrewMemberEntity(
    @PrimaryKey
    @ColumnInfo(FIELD_ID)
    override val id: String,
    @ColumnInfo(FIELD_NAME)
    val name: String,
    val agency: String,
    val image: String,
    val wikipedia: String,
    val status: String,
    val createdAt: Long,
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "crew_member_dbo"
        const val FIELD_ID = "id"
        const val FIELD_NAME = "name"
    }
}

fun CrewMemberEntity.asExternalModel() = CrewMember(
    id = id,
    name = name,
    agency = agency,
    image = image,
    wikipedia = wikipedia,
    status = status,
)