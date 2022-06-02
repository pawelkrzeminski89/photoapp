package com.example.photoapp.usecases

import com.example.photoapp.model.PixabaySearchArguments
import com.example.photoapp.remote.NetworkResult
import com.example.photoapp.remote.PixabayApiResponse
import com.example.photoapp.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPixabayImage @Inject constructor(private val repository: Repository): UseCase(repository) {

    val savePixabayCache = SavePixabayCache(repository)
    val loadPixabayCache = LoadPixabayCache(repository)

    operator fun invoke(pixabaySearchArguments:PixabaySearchArguments): Flow<NetworkResult<PixabayApiResponse>> {
        return flow{
            emit(NetworkResult.Loading())
            try {
                val networkResult = repository.getPixabayImages(pixabaySearchArguments)
                when(networkResult){
                    is NetworkResult.Success -> {
                        networkResult.data?.let {
                            savePixabayCache(networkResult.data.hits, pixabaySearchArguments.preparePixabayApiQueryArgument())
                        }
                        emit(networkResult)
                    }
                    is NetworkResult.Error -> {
                        val cache = loadPixabayCache(pixabaySearchArguments.preparePixabayApiQueryArgument())
                        if (cache.hits.isNotEmpty()){
                            emit(NetworkResult.Success(cache))
                        }
                        else {
                            emit(networkResult) //emit error because cache is empty for this query
                        }
                    }
                    else -> {
                       //donothink
                    }
                }
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message!!, null))
            }
        }
    }
}