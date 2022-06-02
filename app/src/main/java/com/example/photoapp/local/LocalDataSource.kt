package com.example.photoapp.local

interface LocalDataSource {

    suspend fun getPixabayImageByQuery(argument: String): List<PixabayImageEntity>
    suspend fun insertPixabayImageEntity(pixabayImageEntity: PixabayImageEntity)
    suspend fun insertAllPixabayImageEntity(list: List<PixabayImageEntity>)

}