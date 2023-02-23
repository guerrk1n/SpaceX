package com.app.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.core.database.model.LaunchpadEntity

@Dao
interface LaunchpadsDao {

    @Query("SELECT * FROM launchpad_dbo ORDER BY name ASC")
    fun getAllAsc(): PagingSource<Int, LaunchpadEntity>

    @Query("SELECT * FROM launchpad_dbo ORDER BY name DESC")
    fun getAllDesc(): PagingSource<Int, LaunchpadEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rockets: List<LaunchpadEntity>)

    @Query("DELETE FROM launchpad_dbo")
    suspend fun clearAll()

    @Query("SELECT * FROM launchpad_dbo ORDER BY id DESC LIMIT 1")
    suspend fun getLast(): LaunchpadEntity

    @Query("SELECT * FROM launchpad_dbo WHERE id = :id")
    suspend fun getItemById(id: String): LaunchpadEntity
}