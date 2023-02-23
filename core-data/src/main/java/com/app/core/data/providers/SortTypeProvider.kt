package com.app.core.data.providers

import com.app.core.data.preference.UserDataStore
import com.app.core.model.SortType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SortTypeProvider @Inject constructor(
    private val userDataStore: UserDataStore,
) {
    suspend fun getSortType(dataType: DataType): SortType {
        val sortTypeFlow = when (dataType) {
            DataType.Rockets -> userDataStore.getRocketSortType()
            DataType.CrewMembers -> userDataStore.getCrewMembersSortType()
            DataType.HistoryEvents -> userDataStore.getHistoryEventsSortType()
            DataType.Launchpads -> userDataStore.getLaunchpadSortType()
        }
        return withContext(Dispatchers.IO) { sortTypeFlow.first() }
    }

    suspend fun saveSortType(sortType: SortType, dataType: DataType) {
        when (dataType) {
            DataType.Rockets -> userDataStore.saveRocketSortType(sortType)
            DataType.CrewMembers -> userDataStore.saveCrewMembersSortType(sortType)
            DataType.HistoryEvents -> userDataStore.saveHistoryEventsSortType(sortType)
            DataType.Launchpads -> userDataStore.saveLaunchpadSortType(sortType)
        }
    }

    fun getSortTypeFlow(dataType: DataType): Flow<SortType> {
        return when (dataType) {
            DataType.Rockets -> userDataStore.getRocketSortType()
            DataType.CrewMembers -> userDataStore.getCrewMembersSortType()
            DataType.HistoryEvents -> userDataStore.getHistoryEventsSortType()
            DataType.Launchpads -> userDataStore.getLaunchpadSortType()
        }
    }
}