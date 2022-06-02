package com.example.photoapp.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PixabayImageDao {

    @Query("SELECT * from pixabayCache where `query` like :argument")
    suspend fun getPixabayImageByQuery(argument: String): List<PixabayImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPixabayImageEntity(pixabayImageEntity: PixabayImageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    suspend fun insertAllPixabayImageEntity(list: List<PixabayImageEntity>)
}