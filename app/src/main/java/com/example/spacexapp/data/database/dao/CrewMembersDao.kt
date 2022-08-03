package com.example.spacexapp.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.spacexapp.data.database.model.CrewMemberEntity
import com.example.spacexapp.data.database.model.RocketEntity

@Dao
interface CrewMembersDao {

    @Query("SELECT * FROM crew_member_dbo")
    fun getAllItems(): PagingSource<Int, CrewMemberEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rockets: List<CrewMemberEntity>)

    @Query("DELETE FROM crew_member_dbo")
    suspend fun clearItems()

}