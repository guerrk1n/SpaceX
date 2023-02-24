package com.app.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.app.core.database.model.rocket.RocketEntity
import com.app.core.database.model.rocket.RocketImageEntity
import com.app.core.database.model.rocket.RocketResultEntity

@Dao
interface RocketsDao {

    @Transaction
    @Query("""
        SELECT * 
        FROM ${RocketEntity.TABLE_NAME} 
        ORDER BY ${RocketEntity.FIELD_NAME} ASC
    """)
    fun getAllAsc(): PagingSource<Int, RocketResultEntity>

    @Transaction
    @Query("""
        SELECT * 
        FROM ${RocketEntity.TABLE_NAME} 
        ORDER BY ${RocketEntity.FIELD_NAME} DESC
    """)
    fun getAllDesc(): PagingSource<Int, RocketResultEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRocketWithImages(
        rocketEntity: RocketEntity,
        rocketImages: List<RocketImageEntity>
    )

    @Query("""
        DELETE 
        FROM ${RocketEntity.TABLE_NAME}
    """)
    suspend fun clearAll()

    @Query("""
        SELECT ${RocketEntity.FIELD_CREATED_AT} 
        FROM ${RocketEntity.TABLE_NAME} 
        ORDER BY ${RocketEntity.FIELD_CREATED_AT} ASC 
        LIMIT 1
    """)
    suspend fun getLastCreatedAtTime(): Long

    @Query("""
        SELECT * 
        FROM ${RocketEntity.TABLE_NAME} 
        WHERE ${RocketEntity.FIELD_ID} = :id
    """)
    @Transaction
    suspend fun getItemById(id: String): RocketResultEntity
}