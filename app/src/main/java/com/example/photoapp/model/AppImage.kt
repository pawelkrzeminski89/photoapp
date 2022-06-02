package com.example.photoapp.model

import android.os.Parcelable

import com.example.photoapp.remote.PixabayHitResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppImage(
    val id:Long,
    val tags:String,
    val thunbnail:String,
    val imageUrl:String,
    val views:Long,
    val downloads:Long,
    val likes:Long,
    val comments:Long,
    val userName:String
): Parcelable {

    companion object {
        fun FromPixabayHitResponse(pixabayHitResponse: PixabayHitResponse): AppImage {
            return AppImage(
                pixabayHitResponse.id,
                pixabayHitResponse.tags,
                pixabayHitResponse.thunbnail,
                pixabayHitResponse.imageUrl,
                pixabayHitResponse.views,
                pixabayHitResponse.downloads,
                pixabayHitResponse.likes,
                pixabayHitResponse.comments,
                pixabayHitResponse.userName
            )
        }
    }
}