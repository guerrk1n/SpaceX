package com.app.feature.crew

import com.app.core.model.SortType

sealed class CrewMembersAction {

    class ChangeSortType(val type: SortType) : CrewMembersAction()
}