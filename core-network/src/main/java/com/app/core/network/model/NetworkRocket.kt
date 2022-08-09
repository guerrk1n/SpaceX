package com.app.core.network.model

import com.google.gson.annotations.SerializedName

data class NetworkRockets(
    @SerializedName("docs")
    val rockets: List<NetworkRocket>,
    val totalPages: Int,
    val page: Int,
)

data class NetworkRocket(
    val id: String,
    val name: String,
    val active: Boolean,
    @SerializedName("flickr_images")
    val images: List<String>,
    @SerializedName("first_flight")
    val firstFlight: String,
    val wikipedia: String,
    val description: String,
)