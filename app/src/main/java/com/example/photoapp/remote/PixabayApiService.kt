package com.example.photoapp.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApiService {

    @GET(".")
    suspend fun getPixabayImages(
        @Query("key") key:String,
        @Query("q") query:String,
        @Query("image_type") imageType:String = "photo"
    ): Response<PixabayApiResponse>

}