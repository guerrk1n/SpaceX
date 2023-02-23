package com.app.core.network.model

import com.google.gson.annotations.SerializedName

data class NetworkCrewMembers(
    @SerializedName("docs")
    val crewMembers: List<NetworkCrewMember>,
    val totalPages: Int,
    val page: Int,
)

data class NetworkCrewMember(
    val id: String,
    @SerializedName(FIELD_NAME)
    val name: String,
    val agency: String,
    val image: String,
    val wikipedia: String,
    val status: String,
) {
    companion object {
        const val FIELD_NAME = "name"
    }
}