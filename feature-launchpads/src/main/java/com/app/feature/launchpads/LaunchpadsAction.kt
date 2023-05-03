package com.app.feature.launchpads

import com.app.core.model.sort.LaunchpadSortType

sealed class LaunchpadsAction {

    class ChangeSortType(val type: LaunchpadSortType) : LaunchpadsAction()

    class ChangeQuery(val query: String) : LaunchpadsAction()
}