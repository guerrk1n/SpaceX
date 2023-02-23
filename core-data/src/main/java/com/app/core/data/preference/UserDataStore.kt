package com.app.core.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.app.core.model.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserPreference {

    override fun getRocketSortType(): Flow<SortType> {
        return dataStore.data
            .map {
                when (it[ROCKET_SORT_TYPE_PREFERENCE_KEY]) {
                    SortType.ASC.value -> SortType.ASC
                    SortType.DESC.value -> SortType.DESC
                    else -> SortType.ASC
                }
            }
    }

    override suspend fun saveRocketSortType(sortType: SortType) {
        dataStore.edit {
            it[ROCKET_SORT_TYPE_PREFERENCE_KEY] = sortType.value
        }
    }
}



