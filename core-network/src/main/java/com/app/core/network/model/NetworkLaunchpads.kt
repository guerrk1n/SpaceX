package com.app.core.network.model

import com.google.gson.annotations.SerializedName

data class NetworkLaunchpads(
    @SerializedName("docs")
    val launchpads: List<NetworkLaunchpad>,
    val totalPages: Int,
    val page: Int,
)

data class NetworkLaunchpad(
    val id: String,
    @SerializedName(FIELD_NAME)
    val name: String,
    @SerializedName("full_name")
    val fullName: String,
    val status: String,
    val locality: String,
    val region: String,
    @SerializedName("launch_attempts")
    val launchAttempts: Int,
    @SerializedName("launch_successes")
    val launchSuccesses: Int,
    val images: NetworkImages,
    val details: String,
    val launches: List<String>,
    val rockets: List<String>,
    @SerializedName("timezone")
    val timeZone: String,
) {
    companion object {
        const val FIELD_NAME = "name"
    }
}