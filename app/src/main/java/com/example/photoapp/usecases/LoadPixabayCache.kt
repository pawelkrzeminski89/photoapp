package com.example.photoapp.usecases


import com.example.photoapp.remote.PixabayApiResponse
import com.example.photoapp.repository.Repository
import javax.inject.Inject

class LoadPixabayCache @Inject constructor(private val repository: Repository): UseCase(repository) {

    suspend operator fun invoke(query:String): PixabayApiResponse {
        val pixbayImagesFromDatabase = repository.getPixabayImageByQuery(query)
        if(pixbayImagesFromDatabase.isNotEmpty()) {
            val pixabayApiResponse = PixabayApiResponse(
                pixbayImagesFromDatabase.map { it -> it.toPixabayHitResponse() }
            )
            return pixabayApiResponse
        }
        return PixabayApiResponse(emptyList())
    }
}