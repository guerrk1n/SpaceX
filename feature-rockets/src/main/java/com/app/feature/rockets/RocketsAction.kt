package com.app.feature.rockets

import com.app.core.model.SortType

sealed class RocketsAction {

    class ChangeSortType(val type: SortType) : RocketsAction()
}