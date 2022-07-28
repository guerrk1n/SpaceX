package com.example.spacexapp.data.rockets

import com.google.gson.annotations.SerializedName

data class RocketDetailsResponse(
    val name: String,
    val active: Boolean,
    @SerializedName("flickr_images")
    val images: List<String>,
    val id: String,
    @SerializedName("first_flight")
    val firstFlight: String,
    val wikipedia: String,
    val description: String,
    @SerializedName("cost_per_launch")
    val launchCost: Int,
)