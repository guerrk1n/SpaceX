package com.app.spacexapp.model.local.mappers

import com.app.spacexapp.model.local.entities.CrewMemberEntity
import com.app.spacexapp.ui.screens.maintabs.crew.member.CrewMember
import com.app.spacexapp.util.Mapper

class CrewMemberEntityToCrewMemberMapper : Mapper<CrewMemberEntity, CrewMember> {
    override fun map(input: CrewMemberEntity) = CrewMember(
        input.id,
        input.name,
        input.agency,
        input.image,
        input.wikipedia,
        input.status,
    )
}