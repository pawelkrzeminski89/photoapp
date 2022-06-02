package com.example.photoapp.remote


sealed class NetworkResult< out T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success< out T>(data: T): NetworkResult<T>(data)

    class Error<T>(message: String, data: T? = null): NetworkResult<T>(data, message)

    data class Loading(val nothing: Nothing? = null): NetworkResult<Nothing>()

    data class EmptyData(val nothing: Nothing? = null): NetworkResult<Nothing>()

}