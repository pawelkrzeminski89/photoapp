package com.example.photoapp.remote

import com.google.gson.annotations.SerializedName


data class PixabayApiResponse(

    @SerializedName("hits")
    val hits:List<PixabayHitResponse>
)

data class PixabayHitResponse(
    @SerializedName("id")
    val id:Long,
    @SerializedName("tags")
    val tags:String,
    @SerializedName("previewURL")
    val thunbnail:String,
    @SerializedName("largeImageURL")
    val imageUrl:String,
    @SerializedName("views")
    val views:Long,
    @SerializedName("downloads")
    val downloads:Long,
    @SerializedName("likes")
    val likes:Long,
    @SerializedName("comments")
    val comments:Long,
    @SerializedName("user")
    val userName:String
)