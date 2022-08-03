package com.example.spacexapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.spacexapp.model.local.entities.CrewMemberEntity

@Dao
interface CrewMembersDao {

    @Query("SELECT * FROM crew_member_dbo")
    fun getAllItems(): PagingSource<Int, CrewMemberEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rockets: List<CrewMemberEntity>)

    @Query("DELETE FROM crew_member_dbo")
    suspend fun clearItems()

}