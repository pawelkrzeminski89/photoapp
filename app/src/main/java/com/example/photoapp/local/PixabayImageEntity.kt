package com.example.photoapp.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.photoapp.remote.PixabayHitResponse

@Entity(tableName = "pixabayCache")
data class PixabayImageEntity(
    @PrimaryKey
    val id:Long,
    @ColumnInfo(name = "tags")
    val tags:String,
    @ColumnInfo(name = "thunbnail")
    val thunbnail:String,
    @ColumnInfo(name = "imageUrl")
    val imageUrl:String,
    @ColumnInfo(name = "views")
    val views:Long,
    @ColumnInfo(name = "downloads")
    val downloads:Long,
    @ColumnInfo(name = "likes")
    val likes:Long,
    @ColumnInfo(name = "comments")
    val comments:Long,
    @ColumnInfo(name = "userName")
    val userName:String,
    @ColumnInfo(name = "query")
    val query:String
){
    companion object{
        fun FromPixabayHitResponse(
            pixabayHitResponse: PixabayHitResponse,
            prepareApiQueryArgument: String
        ): PixabayImageEntity {
            return PixabayImageEntity(
                pixabayHitResponse.id,
                pixabayHitResponse.tags,
                pixabayHitResponse.thunbnail,
                pixabayHitResponse.imageUrl,
                pixabayHitResponse.views,
                pixabayHitResponse.downloads,
                pixabayHitResponse.likes,
                pixabayHitResponse.comments,
                pixabayHitResponse.userName,
                prepareApiQueryArgument
            )
        }
    }

    fun toPixabayHitResponse(): PixabayHitResponse {
        return PixabayHitResponse(
            this.id,
            this.tags,
            this.thunbnail,
            this.imageUrl,
            this.views,
            this.downloads,
            this.likes,
            this.comments,
            this.userName
        )
    }
}
