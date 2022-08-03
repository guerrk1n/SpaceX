package com.example.spacexapp.ui.screens.maintabs.crew.member

import com.example.spacexapp.data.database.model.CrewMemberEntity
import com.example.spacexapp.data.network.CrewMemberResponse
import com.example.spacexapp.util.Mapper

class CrewMemberResponseMapper : Mapper<CrewMemberResponse, CrewMemberEntity> {
    override fun map(input: CrewMemberResponse) = CrewMemberEntity(
        input.id,
        input.name,
        input.agency,
        input.image,
        input.wikipedia,
        input.status,
    )
}