package com.example.dttassesmentandroid.network

import com.example.dttassesmentandroid.common.Constants
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

//This class is used to add the API key to the request.
class AuthInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder: Request.Builder =
            original.newBuilder().addHeader(Constants.API_KEY_HEADER, apiKey).url(original.url)
        return chain.proceed(requestBuilder.build())
    }
}