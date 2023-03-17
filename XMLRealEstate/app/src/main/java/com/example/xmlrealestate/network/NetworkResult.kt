package com.example.xmlrealestate.network

/**
This class used to handle network results in a generic way, allowing you to define [Success], [Error], and [Loading] states for any type of data.
 */
sealed class NetworkResult<T>(
    var data: T? = null, val message: String? = null
) {
    /**
     * A class that represents a successful result of a network operation.
     * @param data the data returned by the network operation
     */
    class Success<T>(data: T) : NetworkResult<T>(data)

    /**
     * A class that represents an error result of a network operation.
     * @param message the error message returned by the network operation
     * @param data the data returned by the network operation, could be null
     */
    class Error<T>(message: String, data: T? = null) : NetworkResult<T>(data, message)

    /**
     * A class that represents a loading state of a network operation.
     */
    class Loading<T> : NetworkResult<T>()

    /**
     * Transforms the data of the network result using the specified transform function.
     * @param transform the transform function that maps the data of the network result to a new type R
     * @return a new NetworkResult instance that contains the transformed data
     */
    fun <R> map(transform: (data: T?) -> R): NetworkResult<R> = when (this) {
        is Error -> Error(message!!, transform(data))
        is Loading -> Loading()
        is Success -> Success(transform(data))
    }
}