package com.app.core.data.providers

import com.app.core.data.preference.UserDataStore
import com.app.core.model.sort.LaunchpadSortType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LaunchpadSortTypeProvider @Inject constructor(
    private val userDataStore: UserDataStore,
) : SortTypeProvider<LaunchpadSortType> {

    override suspend fun getSortType(): LaunchpadSortType {
        return withContext(Dispatchers.IO) { getSortTypeFlow().first() }
    }

    override suspend fun saveSortType(sortType: LaunchpadSortType) {
        userDataStore.saveLaunchpadSortType(sortType)
    }

    override fun getSortTypeFlow(): Flow<LaunchpadSortType> {
       return userDataStore.getLaunchpadSortType()
    }
}