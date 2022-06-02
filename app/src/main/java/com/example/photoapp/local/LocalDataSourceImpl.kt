package com.example.photoapp.local

class LocalDataSourceImpl(private val pixabayImageDao: PixabayImageDao) : LocalDataSource {

    override suspend fun getPixabayImageByQuery(argument: String): List<PixabayImageEntity> {
        return pixabayImageDao.getPixabayImageByQuery(argument)
    }

    override suspend fun insertPixabayImageEntity(pixabayImageEntity: PixabayImageEntity) {
        pixabayImageDao.insertPixabayImageEntity(pixabayImageEntity)
    }

    override suspend fun insertAllPixabayImageEntity(list: List<PixabayImageEntity>) {
        pixabayImageDao.insertAllPixabayImageEntity(list)
    }
}