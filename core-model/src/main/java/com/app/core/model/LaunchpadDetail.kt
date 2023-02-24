package com.app.core.model

data class LaunchpadDetail(
    val id: String,
    val name: String,
    val fullName: String,
    val status: String,
    val locality: String,
    val region: String,
    val landingAttempts: Int,
    val landingSuccesses: Int,
    val images: List<String>,
    val details: String,
    val rockets: List<String>,
    val timeZone: String,
)