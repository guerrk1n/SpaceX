package com.app.feature.crew

import com.app.core.model.sort.CrewMemberSortType

sealed class CrewMembersAction {

    class ChangeSortType(val type: CrewMemberSortType) : CrewMembersAction()
}