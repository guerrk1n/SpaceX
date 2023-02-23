package com.app.feature.launchpads

import com.app.core.model.SortType

sealed class LaunchpadsAction {

    class ChangeSortType(val type: SortType) : LaunchpadsAction()
}