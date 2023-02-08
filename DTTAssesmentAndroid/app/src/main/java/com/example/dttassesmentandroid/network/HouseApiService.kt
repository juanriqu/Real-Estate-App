package com.example.dttassesmentandroid.network

import com.example.dttassesmentandroid.data.model.HouseResponse
import retrofit2.Response
import retrofit2.http.GET

interface HouseApiService {
    @GET("house")
    suspend fun getAllHouses(): Response<HouseResponse>
}