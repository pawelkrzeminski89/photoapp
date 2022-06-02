package com.example.photoapp.remote

interface RemoteDataSource {

    suspend fun getPixabayImages(query:String):NetworkResult<PixabayApiResponse>

}