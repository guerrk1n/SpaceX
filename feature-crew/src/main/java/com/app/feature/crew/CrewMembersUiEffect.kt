package com.app.feature.crew

sealed class CrewMembersUiEffect {
    class ChangeSortType: CrewMembersUiEffect()

    class QueryChanged: CrewMembersUiEffect()
}