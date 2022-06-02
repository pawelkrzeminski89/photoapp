package com.example.photoapp.local


import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [PixabayImageEntity::class], version = 1, exportSchema = false)
abstract class AppDatabaseObject : RoomDatabase() {

    abstract fun pixabayImageDao(): PixabayImageDao

}