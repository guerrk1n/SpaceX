package com.example.spacexapp.model.remote.mappers

import com.example.spacexapp.model.local.entities.CrewMemberEntity
import com.example.spacexapp.model.remote.responses.CrewMemberResponse
import com.example.spacexapp.util.Mapper

class CrewMemberResponseMapper : Mapper<CrewMemberResponse, CrewMemberEntity> {
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