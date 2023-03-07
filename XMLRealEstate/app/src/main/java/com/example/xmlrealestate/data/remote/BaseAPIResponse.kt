package com.example.xmlrealestate.data.remote

import com.example.xmlrealestate.network.NetworkResult
import retrofit2.Response
import timber.log.Timber

/**
 * An abstract class that provides a generic implementation of a safe API call that returns a [NetworkResult]
 * based on the response from the API.
 */
abstract class BaseAPIResponse {
    /**
     * Executes an API call using Retrofit and returns a [NetworkResult] based on the response.
     *
     * @param apiCall a suspend function that makes the API call using Retrofit and returns a response of type [Response<R>].
     * @param transform a function that takes the response body of type [R] and transforms it into the desired output type of [T].
     * @return a [NetworkResult] containing the result of the API call.
     */
    suspend fun <T, R> safeApiCall(
        apiCall: suspend () -> Response<R>, transform: (R) -> T
    ): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(transform(body))
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    /**
     * Helper function that returns a [NetworkResult.Error] object with the given error message.
     *
     * @param errorMessage the error message to include in the error object.
     * @return a [NetworkResult.Error] object with the given error message.
     */
    private fun <T> error(errorMessage: String): NetworkResult<T> {
        Timber.e("Api call failed $errorMessage")
        return NetworkResult.Error("Api call failed $errorMessage")
    }
}

