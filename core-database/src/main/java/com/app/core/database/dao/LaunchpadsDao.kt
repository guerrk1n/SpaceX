package com.app.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.app.core.database.model.launchpad.LaunchpadEntity
import com.app.core.database.model.launchpad.LaunchpadImageEntity
import com.app.core.database.model.launchpad.LaunchpadWithImagesEntity

@Dao
interface LaunchpadsDao {

    @Transaction
    @Query(
        """
        SELECT * 
        FROM ${LaunchpadEntity.TABLE_NAME}
        ORDER BY ${LaunchpadEntity.FIELD_NAME} ASC
    """
    )
    fun getAllAsc(): PagingSource<Int, LaunchpadWithImagesEntity>

    @Transaction
    @Query(
        """
        SELECT * 
        FROM ${LaunchpadEntity.TABLE_NAME} 
        ORDER BY ${LaunchpadEntity.FIELD_NAME} DESC
    """
    )
    fun getAllDesc(): PagingSource<Int, LaunchpadWithImagesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunchpadWithImages(
        launchpadEntity: LaunchpadEntity,
        launchpadImages: List<LaunchpadImageEntity>
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
    suspend fun getItemById(id: String): LaunchpadWithImagesEntity
}