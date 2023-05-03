package com.app.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.app.core.database.model.launchpad.LaunchpadEntity
import com.app.core.database.model.launchpad.LaunchpadImageEntity
import com.app.core.database.model.launchpad.LaunchpadResultEntity
import com.app.core.database.model.crossref.LaunchpadRocketCrossRefEntity

@Dao
interface LaunchpadsDao {

    @Transaction
    @Query(
        """
        SELECT * 
        FROM ${LaunchpadEntity.TABLE_NAME}
        WHERE ${LaunchpadEntity.FIELD_FULL_NAME} LIKE '%' || :query || '%'
        ORDER BY ${LaunchpadEntity.FIELD_NAME} ASC
    """
    )
    fun getAllAsc(query: String = ""): PagingSource<Int, LaunchpadResultEntity>

    @Transaction
    @Query(
        """
        SELECT * 
        FROM ${LaunchpadEntity.TABLE_NAME} 
        WHERE ${LaunchpadEntity.FIELD_FULL_NAME} LIKE '%' || :query || '%'
        ORDER BY ${LaunchpadEntity.FIELD_NAME} DESC
    """
    )
    fun getAllDesc(query: String = ""): PagingSource<Int, LaunchpadResultEntity>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunchpadData(
        launchpad: LaunchpadEntity,
        launchpadImages: List<LaunchpadImageEntity>,
        launchpadRocketCrossRef: List<LaunchpadRocketCrossRefEntity>,
    )

    @Query(
        """
        DELETE 
        FROM ${LaunchpadEntity.TABLE_NAME}
    """
    )
    suspend fun clearAll()

    @Query(
        """
        SELECT ${LaunchpadEntity.FIELD_CREATED_AT}
        FROM ${LaunchpadEntity.TABLE_NAME} 
        ORDER BY ${LaunchpadEntity.FIELD_ID} DESC 
        LIMIT 1
    """
    )
    suspend fun getLastCreatedAtTime(): Long?

    @Transaction
    @Query(
        """
        SELECT * 
        FROM ${LaunchpadEntity.TABLE_NAME} 
        WHERE ${LaunchpadEntity.FIELD_ID} = :id
    """
    )
    suspend fun getItemById(id: String): LaunchpadResultEntity
}