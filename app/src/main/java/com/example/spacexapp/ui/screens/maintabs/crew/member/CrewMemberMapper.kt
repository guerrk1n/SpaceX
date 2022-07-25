package com.example.spacexapp.ui.screens.maintabs.crew.member

import com.example.spacexapp.data.crew.CrewMemberResponse
import com.example.spacexapp.util.Mapper

class CrewMemberMapper : Mapper<CrewMemberResponse, CrewMember> {
    override fun map(input: CrewMemberResponse) = CrewMember(
        input.name,
        input.agency,
        input.image,
        input.wikipedia,
        input.status,
        input.id,
    )
}