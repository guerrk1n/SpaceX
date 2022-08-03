package com.example.spacexapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.spacexapp.model.local.entities.RocketEntity

@Dao
interface RocketsDao {

    @Query("SELECT * FROM rocket_dbo")
    fun getAllItems(): PagingSource<Int, RocketEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rockets: List<RocketEntity>)

    @Query("DELETE FROM rocket_dbo")
    suspend fun clearItems()

}