package com.example.dttassesmentandroid.data.remote

import com.example.dttassesmentandroid.network.NetworkResult
import retrofit2.Response

//This class is used to handle the response from the API calls with a generic way.
abstract class BaseAPIResponse {
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

    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed $errorMessage")
}