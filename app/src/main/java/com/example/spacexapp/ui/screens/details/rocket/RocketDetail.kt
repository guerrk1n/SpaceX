package com.example.spacexapp.ui.screens.details.rocket

data class RocketDetail(
    val name: String,
    val active: Boolean,
    val images: List<String>,
    val id: String,
    val firstFlight: String,
    val wikipedia: String,
    val description: String,
    val launchCost: Int,
)