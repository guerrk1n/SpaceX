package com.app.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.core.database.model.HistoryEventEntity

@Dao
interface HistoryEventsDao {

    @Query(
        """
        SELECT * 
        FROM ${HistoryEventEntity.TABLE_NAME}
        WHERE ${HistoryEventEntity.FIELD_TITLE} LIKE '%' || :query || '%'
        ORDER BY ${HistoryEventEntity.FIELD_TITLE}  ASC
    """
    )
    fun getAllByNameAsc(query: String = ""): PagingSource<Int, HistoryEventEntity>

    @Query(
        """
        SELECT * 
        FROM ${HistoryEventEntity.TABLE_NAME}  
        WHERE ${HistoryEventEntity.FIELD_TITLE} LIKE '%' || :query || '%'
        ORDER BY ${HistoryEventEntity.FIELD_TITLE} DESC
    """
    )
    fun getAllByNameDesc(query: String = ""): PagingSource<Int, HistoryEventEntity>

    @Query(
        """
        SELECT * 
        FROM ${HistoryEventEntity.TABLE_NAME}  
        WHERE ${HistoryEventEntity.FIELD_TITLE} LIKE '%' || :query || '%'
        ORDER BY ${HistoryEventEntity.FIELD_DATE} ASC
    """
    )
    fun getAllByDateAsc(query: String = ""): PagingSource<Int, HistoryEventEntity>

    @Query(
        """
        SELECT * 
        FROM ${HistoryEventEntity.TABLE_NAME}  
        WHERE ${HistoryEventEntity.FIELD_TITLE} LIKE '%' || :query || '%'
        ORDER BY ${HistoryEventEntity.FIELD_DATE} DESC
    """
    )
    fun getAllByDateDesc(query: String = ""): PagingSource<Int, HistoryEventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rockets: List<HistoryEventEntity>)

    @Query(
        """
        DELETE 
        FROM ${HistoryEventEntity.TABLE_NAME} 
    """
    )
    suspend fun clearAll()

    @Query(
        """
        SELECT * 
        FROM ${HistoryEventEntity.TABLE_NAME}  
        ORDER BY ${HistoryEventEntity.FIELD_ID} DESC 
        LIMIT 1
    """
    )
    suspend fun getLast(): HistoryEventEntity
}