package com.app.core.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.app.core.model.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserPreference {

    override fun getRocketSortType(): Flow<SortType> {
        return dataStore.data.mapSortType(ROCKET_SORT_TYPE_PREFERENCE_KEY)
    }

    override suspend fun saveRocketSortType(sortType: SortType) {
        dataStore.edit {
            it[ROCKET_SORT_TYPE_PREFERENCE_KEY] = sortType.value
        }
    }

    override fun getCrewMembersSortType(): Flow<SortType> {
        return dataStore.data.mapSortType(CREW_MEMBER_SORT_TYPE_PREFERENCE_KEY)
    }

    override suspend fun saveCrewMembersSortType(sortType: SortType) {
        dataStore.edit {
            it[CREW_MEMBER_SORT_TYPE_PREFERENCE_KEY] = sortType.value
        }
    }

    override fun getHistoryEventsSortType(): Flow<SortType> {
        return dataStore.data.mapSortType(HISTORY_EVENTS_SORT_TYPE_PREFERENCE_KEY)
    }

    override suspend fun saveHistoryEventsSortType(sortType: SortType) {
        dataStore.edit {
            it[HISTORY_EVENTS_SORT_TYPE_PREFERENCE_KEY] = sortType.value
        }
    }

    override fun getLaunchpadSortType(): Flow<SortType> {
        return dataStore.data.mapSortType(LAUNCHPADS_SORT_TYPE_PREFERENCE_KEY)
    }

    override suspend fun saveLaunchpadSortType(sortType: SortType) {
        dataStore.edit {
            it[LAUNCHPADS_SORT_TYPE_PREFERENCE_KEY] = sortType.value
        }
    }

    private fun Flow<Preferences>.mapSortType(sortKey: Preferences.Key<String>): Flow<SortType> = transform { value ->
        val transformation: suspend (value: Preferences) -> SortType = {
            when (it[sortKey]) {
                SortType.NAME_ASC.value -> SortType.NAME_ASC
                SortType.NAME_DESC.value -> SortType.NAME_DESC
                else -> SortType.NAME_ASC
            }
        }
        return@transform emit(transformation(value))
    }
}



