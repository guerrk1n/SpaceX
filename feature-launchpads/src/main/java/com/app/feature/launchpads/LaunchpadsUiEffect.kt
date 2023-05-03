package com.app.feature.launchpads

sealed class LaunchpadsUiEffect {
    class ChangeSortType: LaunchpadsUiEffect()

    class QueryChanged: LaunchpadsUiEffect()
}