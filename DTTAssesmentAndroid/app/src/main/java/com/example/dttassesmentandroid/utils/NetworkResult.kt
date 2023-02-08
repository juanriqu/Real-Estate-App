package com.example.dttassesmentandroid.utils

//This class is used to handle the network results in a generic way so that we can use it for any type of data.
sealed class NetworkResult<T>(
    var data: T? = null, val message: String? = null
) {
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Error<T>(message: String, data: T? = null) : NetworkResult<T>(data, message)
    class Loading<T> : NetworkResult<T>()

    fun <R> map(transform: (data: T?) -> R): NetworkResult<R> = when (this) {
        is Error -> Error(message!!, transform(data))
        is Loading -> Loading()
        is Success -> Success(transform(data))
    }
}