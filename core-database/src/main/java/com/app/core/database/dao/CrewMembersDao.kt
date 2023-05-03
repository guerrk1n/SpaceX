package com.app.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.core.database.model.CrewMemberEntity

@Dao
interface CrewMembersDao {

    @Query(
        """
        SELECT * 
        FROM ${CrewMemberEntity.TABLE_NAME} 
        WHERE ${CrewMemberEntity.FIELD_NAME} LIKE '%' || :query || '%'
        ORDER BY ${CrewMemberEntity.FIELD_NAME}  ASC
    """
    )
    fun getAllAsc(query: String = ""): PagingSource<Int, CrewMemberEntity>

    @Query(
        """
        SELECT * 
        FROM ${CrewMemberEntity.TABLE_NAME}  
        WHERE ${CrewMemberEntity.FIELD_NAME} LIKE '%' || :query || '%'
        ORDER BY ${CrewMemberEntity.FIELD_NAME} DESC
    """
    )
    fun getAllDesc(query: String = ""): PagingSource<Int, CrewMemberEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(crewMembers: List<CrewMemberEntity>)

    @Query(
        """
        DELETE 
        FROM ${CrewMemberEntity.TABLE_NAME} 
    """
    )
    suspend fun clearAll()

    @Query(
        """
        SELECT * 
        FROM ${CrewMemberEntity.TABLE_NAME}  
        ORDER BY ${CrewMemberEntity.FIELD_ID} DESC 
        LIMIT 1
    """
    )
    suspend fun getLast(): CrewMemberEntity

}