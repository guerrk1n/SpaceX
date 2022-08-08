package com.app.core.model

data class RocketDetail(
    val id: String,
    val name: String,
    val active: Boolean,
    val images: List<String>,
    val firstFlight: String,
    val wikipedia: String,
    val description: String,
)