package com.example.spacexapp.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.spacexapp.data.database.model.RocketEntity

@Dao
interface RocketDao {

    @Query("SELECT * FROM rocket_dbo")
    fun getAllItems(): PagingSource<Int, RocketEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rockets: List<RocketEntity>)

    @Query("DELETE FROM rocket_dbo")
    suspend fun clearItems()

}