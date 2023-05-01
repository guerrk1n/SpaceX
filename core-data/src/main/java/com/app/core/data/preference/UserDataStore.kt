package com.app.core.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.app.core.model.sort.HistoryEventSortType
import com.app.core.model.sort.CrewMemberSortType
import com.app.core.model.sort.LaunchpadSortType
import com.app.core.model.sort.RocketSortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserPreference {

    override fun getRocketSortType(): Flow<RocketSortType> {
        return dataStore.data.mapRocketSortType(ROCKET_SORT_TYPE_PREFERENCE_KEY)
    }

    override suspend fun saveRocketSortType(sortType: RocketSortType) {
        dataStore.edit {
            it[ROCKET_SORT_TYPE_PREFERENCE_KEY] = sortType.value
        }
    }

    override fun getCrewMembersSortType(): Flow<CrewMemberSortType> {
        return dataStore.data.mapCrewMemberSortType(CREW_MEMBER_SORT_TYPE_PREFERENCE_KEY)
    }

    override suspend fun saveCrewMembersSortType(sortType: CrewMemberSortType) {
        dataStore.edit {
            it[CREW_MEMBER_SORT_TYPE_PREFERENCE_KEY] = sortType.value
        }
    }

    override fun getHistoryEventsSortType(): Flow<HistoryEventSortType> {
        return dataStore.data.mapHistoryEventsSortType(HISTORY_EVENTS_SORT_TYPE_PREFERENCE_KEY)
    }

    override suspend fun saveHistoryEventsSortType(sortType: HistoryEventSortType) {
        dataStore.edit {
            it[HISTORY_EVENTS_SORT_TYPE_PREFERENCE_KEY] = sortType.value
        }
    }

    override fun getLaunchpadSortType(): Flow<LaunchpadSortType> {
        return dataStore.data.mapLaunchpadSortType(LAUNCHPADS_SORT_TYPE_PREFERENCE_KEY)
    }

    override suspend fun saveLaunchpadSortType(sortType: LaunchpadSortType) {
        dataStore.edit {
            it[LAUNCHPADS_SORT_TYPE_PREFERENCE_KEY] = sortType.value
        }
    }

    private fun Flow<Preferences>.mapRocketSortType(sortKey: Preferences.Key<String>): Flow<RocketSortType> = transform { value ->
        val transformation: suspend (value: Preferences) -> RocketSortType = {
            when (it[sortKey]) {
                RocketSortType.NAME_ASC.value -> RocketSortType.NAME_ASC
                RocketSortType.NAME_DESC.value -> RocketSortType.NAME_DESC
                else -> RocketSortType.NAME_ASC
            }
        }
        return@transform emit(transformation(value))
    }

    private fun Flow<Preferences>.mapCrewMemberSortType(sortKey: Preferences.Key<String>): Flow<CrewMemberSortType> = transform { value ->
        val transformation: suspend (value: Preferences) -> CrewMemberSortType = {
            when (it[sortKey]) {
                CrewMemberSortType.NAME_ASC.value -> CrewMemberSortType.NAME_ASC
                CrewMemberSortType.NAME_DESC.value -> CrewMemberSortType.NAME_DESC
                else -> CrewMemberSortType.NAME_ASC
            }
        }
        return@transform emit(transformation(value))
    }

    private fun Flow<Preferences>.mapLaunchpadSortType(sortKey: Preferences.Key<String>): Flow<LaunchpadSortType> = transform { value ->
        val transformation: suspend (value: Preferences) -> LaunchpadSortType = {
            when (it[sortKey]) {
                LaunchpadSortType.NAME_ASC.value -> LaunchpadSortType.NAME_ASC
                LaunchpadSortType.NAME_DESC.value -> LaunchpadSortType.NAME_DESC
                else -> LaunchpadSortType.NAME_ASC
            }
        }
        return@transform emit(transformation(value))
    }

    private fun Flow<Preferences>.mapHistoryEventsSortType(sortKey: Preferences.Key<String>): Flow<HistoryEventSortType> = transform { value ->
        val transformation: suspend (value: Preferences) -> HistoryEventSortType = {
            when (it[sortKey]) {
                HistoryEventSortType.NAME_ASC.value -> HistoryEventSortType.NAME_ASC
                HistoryEventSortType.NAME_DESC.value -> HistoryEventSortType.NAME_DESC
                HistoryEventSortType.DATE_ASC.value -> HistoryEventSortType.DATE_ASC
                HistoryEventSortType.DATE_DESC.value -> HistoryEventSortType.DATE_DESC
                else -> HistoryEventSortType.NAME_ASC
            }
        }
        return@transform emit(transformation(value))
    }
}



