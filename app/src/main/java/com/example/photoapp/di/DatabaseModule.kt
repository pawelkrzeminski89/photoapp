package com.example.photoapp.di

import android.content.Context
import androidx.room.Room
import com.example.photoapp.local.AppDatabaseObject
import com.example.photoapp.local.PixabayImageDao
import com.example.photoapp.local.LocalDataSource
import com.example.photoapp.local.LocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    companion object {
        const val DATABASE_NAME = "PixabayDatabase"
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabaseObject {
        return Room.databaseBuilder(
            appContext,
            AppDatabaseObject::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideImageAppDao(appDatabase: AppDatabaseObject): PixabayImageDao {
        return appDatabase.pixabayImageDao()
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(pixabayImageDao: PixabayImageDao): LocalDataSource {
        return LocalDataSourceImpl(pixabayImageDao)
    }
}