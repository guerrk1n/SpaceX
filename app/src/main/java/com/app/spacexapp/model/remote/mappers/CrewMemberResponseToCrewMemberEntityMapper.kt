package com.app.spacexapp.model.remote.mappers

import com.app.spacexapp.model.local.entities.CrewMemberEntity
import com.app.spacexapp.model.remote.responses.CrewMemberResponse
import com.app.spacexapp.util.Mapper

class CrewMemberResponseToCrewMemberEntityMapper : Mapper<CrewMemberResponse, CrewMemberEntity> {
    override fun map(input: CrewMemberResponse) = CrewMemberEntity(
        input.id,
        input.name,
        input.agency,
        input.image,
        input.wikipedia,
        input.status,
        System.currentTimeMillis()
    )
}