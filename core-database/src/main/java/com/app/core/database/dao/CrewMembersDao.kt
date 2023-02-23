package com.app.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.core.database.model.CrewMemberEntity

@Dao
interface CrewMembersDao {

    @Query("SELECT * FROM crew_member_dbo ORDER BY name ASC")
    fun getAllAsc(): PagingSource<Int, CrewMemberEntity>

    @Query("SELECT * FROM crew_member_dbo ORDER BY name DESC")
    fun getAllDesc(): PagingSource<Int, CrewMemberEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rockets: List<CrewMemberEntity>)

    @Query("DELETE FROM crew_member_dbo")
    suspend fun clearAll()

    @Query("SELECT * FROM crew_member_dbo ORDER BY id DESC LIMIT 1")
    suspend fun getLast(): CrewMemberEntity

}