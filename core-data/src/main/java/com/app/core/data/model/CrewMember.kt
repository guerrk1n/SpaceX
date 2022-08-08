package com.app.core.data.model

import com.app.core.database.model.CrewMemberEntity
import com.app.core.network.model.NetworkCrewMember

fun NetworkCrewMember.asEntity() = CrewMemberEntity(
    id = id,
    name = name,
    agency = agency,
    image = image,
    wikipedia = wikipedia,
    status = status,
    System.currentTimeMillis()
)