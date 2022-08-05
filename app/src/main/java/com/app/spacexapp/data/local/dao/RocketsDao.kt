package com.app.spacexapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.spacexapp.model.local.entities.RocketEntity

@Dao
interface RocketsDao {

    @Query("SELECT * FROM rocket_dbo")
    fun getAll(): PagingSource<Int, RocketEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rockets: List<RocketEntity>)

    @Query("DELETE FROM rocket_dbo")
    suspend fun clearAll()

    @Query("SELECT * FROM rocket_dbo ORDER BY id DESC LIMIT 1")
    suspend fun getLast(): RocketEntity

    @Query("SELECT * FROM rocket_dbo WHERE id = :id")
    suspend fun getItemById(id: String): RocketEntity
}