package com.app.feature.rockets

import com.app.core.model.sort.RocketSortType

sealed class RocketsAction {

    class ChangeSortType(val type: RocketSortType) : RocketsAction()
}