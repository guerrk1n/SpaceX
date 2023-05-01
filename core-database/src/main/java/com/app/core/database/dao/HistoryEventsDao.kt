package com.app.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.core.database.model.HistoryEventEntity

@Dao
interface HistoryEventsDao {

    @Query("SELECT * FROM history_event_dbo ORDER BY title ASC")
    fun getAllByNameAsc(): PagingSource<Int, HistoryEventEntity>

    @Query("SELECT * FROM history_event_dbo ORDER BY title DESC")
    fun getAllByNameDesc(): PagingSource<Int, HistoryEventEntity>

    @Query("SELECT * FROM history_event_dbo ORDER BY date ASC")
    fun getAllByDateAsc(): PagingSource<Int, HistoryEventEntity>

    @Query("SELECT * FROM history_event_dbo ORDER BY date DESC")
    fun getAllByDateDesc(): PagingSource<Int, HistoryEventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rockets: List<HistoryEventEntity>)

    @Query("DELETE FROM history_event_dbo")
    suspend fun clearAll()

    @Query("SELECT * FROM history_event_dbo ORDER BY id DESC LIMIT 1")
    suspend fun getLast(): HistoryEventEntity
}