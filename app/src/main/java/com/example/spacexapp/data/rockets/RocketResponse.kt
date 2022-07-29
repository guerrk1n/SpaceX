package com.example.spacexapp.data.rockets

import com.google.gson.annotations.SerializedName

data class RocketsResponse(
    @SerializedName("docs")
    val rockets: List<RocketResponse>,
    val totalPages: Int,
    val page: Int,
)

data class RocketResponse(
    val name: String,
    val active: Boolean,
    @SerializedName("flickr_images")
    val images: List<String>,
    val id: String,
)