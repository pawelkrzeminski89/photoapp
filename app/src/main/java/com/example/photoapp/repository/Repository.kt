package com.example.photoapp.repository

import com.example.photoapp.local.LocalDataSource
import com.example.photoapp.local.PixabayImageEntity
import com.example.photoapp.remote.NetworkResult
import com.example.photoapp.remote.PixabayApiResponse
import com.example.photoapp.model.PixabaySearchArguments
import com.example.photoapp.remote.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    suspend fun getPixabayImages(pixabaySearchArguments: PixabaySearchArguments) : NetworkResult<PixabayApiResponse> {
       return remoteDataSource.getPixabayImages(pixabaySearchArguments.preparePixabayApiQueryArgument())
    }

    suspend fun getPixabayImageByQuery(argument: String): List<PixabayImageEntity> {
        return localDataSource.getPixabayImageByQuery(argument)
    }

    suspend fun insertAllPixabayImageEntity(list: List<PixabayImageEntity>) {
        localDataSource.insertAllPixabayImageEntity(list)
    }

}