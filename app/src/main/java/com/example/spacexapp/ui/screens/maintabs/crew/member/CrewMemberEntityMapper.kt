package com.example.spacexapp.ui.screens.maintabs.crew.member

import com.example.spacexapp.data.database.model.CrewMemberEntity
import com.example.spacexapp.data.network.CrewMemberResponse
import com.example.spacexapp.util.Mapper

class CrewMemberEntityMapper : Mapper<CrewMemberEntity, CrewMember> {
    override fun map(input: CrewMemberEntity) = CrewMember(
        input.name,
        input.agency,
        input.image,
        input.wikipedia,
        input.status,
        input.id,
    )
}