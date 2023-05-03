package com.app.feature.rockets

sealed class RocketsUiEffect {
    class ChangedSortType: RocketsUiEffect()

    class QueryChanged: RocketsUiEffect()
}