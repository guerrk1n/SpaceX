package com.app.core.data.providers.sort

import com.app.core.data.preference.UserDataStore
import com.app.core.model.sort.RocketSortType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RocketsSortTypeProvider @Inject constructor(
    private val userDataStore: UserDataStore,
) : SortTypeProvider<RocketSortType> {

    override suspend fun getSortType(): RocketSortType {
        return withContext(Dispatchers.IO) { getSortTypeFlow().first() }
    }

    override suspend fun saveSortType(sortType: RocketSortType) {
        userDataStore.saveRocketSortType(sortType)
    }

    override fun getSortTypeFlow(): Flow<RocketSortType> {
       return userDataStore.getRocketSortType()
    }
}