package com.example.spacexapp.model.local.mappers

import com.example.spacexapp.model.local.entities.CrewMemberEntity
import com.example.spacexapp.ui.screens.maintabs.crew.member.CrewMember
import com.example.spacexapp.util.Mapper

class CrewMemberEntityMapper : Mapper<CrewMemberEntity, CrewMember> {
    override fun map(input: CrewMemberEntity) = CrewMember(
        input.id,
        input.name,
        input.agency,
        input.image,
        input.wikipedia,
        input.status,
    )
}