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
    suspend fun getSortType(): SortType {
        return withContext(Dispatchers.IO) { userDataStore.getRocketSortType().first() }
    }

    suspend fun saveRocketSortType(sortType: SortType) {
        userDataStore.saveRocketSortType(sortType)
    }

    fun getSortTypeFlow(): Flow<SortType> {
        return userDataStore.getRocketSortType()
    }
}