package com.app.core.data.providers

import com.app.core.data.preference.UserDataStore
import com.app.core.model.sort.HistoryEventSortType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HistoryEventsSortTypeProvider @Inject constructor(
    private val userDataStore: UserDataStore,
) : SortTypeProvider<HistoryEventSortType> {

    override suspend fun getSortType(): HistoryEventSortType {
        return withContext(Dispatchers.IO) { getSortTypeFlow().first() }
    }

    override suspend fun saveSortType(sortType: HistoryEventSortType) {
        userDataStore.saveHistoryEventsSortType(sortType)
    }

    override fun getSortTypeFlow(): Flow<HistoryEventSortType> {
       return userDataStore.getHistoryEventsSortType()
    }
}