package com.app.core.model

data class Rocket(
    val id: String,
    val name: String,
    val active: Boolean,
    val images: List<String>,
)