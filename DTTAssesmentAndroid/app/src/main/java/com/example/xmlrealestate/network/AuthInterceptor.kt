package com.example.xmlrealestate.network

import com.example.xmlrealestate.common.Constants
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * This AuthInterceptor class is used as an Interceptor to add an API key header to the HTTP request made.
 */
class AuthInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder: Request.Builder =
            original.newBuilder().addHeader(Constants.API_KEY_HEADER, apiKey).url(original.url)
        return chain.proceed(requestBuilder.build())
    }
}
