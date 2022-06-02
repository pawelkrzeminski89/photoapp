package com.example.photoapp.remote

import android.content.Context
import com.example.photoapp.R
import com.example.photoapp.helper.Keys
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val pixabayApiService: PixabayApiService
):BaseApiResponse(), RemoteDataSource {

    override suspend fun getPixabayImages(query: String): NetworkResult<PixabayApiResponse> =
        safeApiCall { pixabayApiService.getPixabayImages(Keys.getPixabayApiKey(), query) }

}