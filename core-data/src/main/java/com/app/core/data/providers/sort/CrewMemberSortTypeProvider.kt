package com.app.core.data.providers.sort

import com.app.core.data.preference.UserDataStore
import com.app.core.model.sort.CrewMemberSortType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CrewMemberSortTypeProvider @Inject constructor(
    private val userDataStore: UserDataStore,
) : SortTypeProvider<CrewMemberSortType> {

    override suspend fun getSortType(): CrewMemberSortType {
        return withContext(Dispatchers.IO) { getSortTypeFlow().first() }
    }

    override suspend fun saveSortType(sortType: CrewMemberSortType) {
        userDataStore.saveCrewMembersSortType(sortType)
    }

    override fun getSortTypeFlow(): Flow<CrewMemberSortType> {
       return userDataStore.getCrewMembersSortType()
    }
}