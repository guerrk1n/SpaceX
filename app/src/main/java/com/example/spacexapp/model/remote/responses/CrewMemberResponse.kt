package com.example.spacexapp.model.remote.responses

import com.google.gson.annotations.SerializedName

data class CrewMembersResponse(
    @SerializedName("docs")
    val crewMembers: List<CrewMemberResponse>,
    val totalPages: Int,
    val page: Int,
)

data class CrewMemberResponse(
    val id: String,
    val name: String,
    val agency: String,
    val image: String,
    val wikipedia: String,
    val status: String,
)