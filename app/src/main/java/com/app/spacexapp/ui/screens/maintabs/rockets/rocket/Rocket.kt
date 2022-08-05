package com.app.spacexapp.ui.screens.maintabs.rockets.rocket

data class Rocket(
    val id: String,
    val name: String,
    val active: Boolean,
    val images: List<String>,
)