package com.example.photoapp.usecases

import com.example.photoapp.local.PixabayImageEntity
import com.example.photoapp.remote.PixabayHitResponse
import com.example.photoapp.repository.Repository
import javax.inject.Inject

class SavePixabayCache @Inject constructor(private val repository: Repository): UseCase(repository) {

    suspend operator fun invoke(pixabayHits:List<PixabayHitResponse>, query:String) {
        repository.insertAllPixabayImageEntity(
            pixabayHits
                .map {
                    PixabayImageEntity.FromPixabayHitResponse(
                        it,
                        query
                    )
                }
                .toList()
        )
    }
}