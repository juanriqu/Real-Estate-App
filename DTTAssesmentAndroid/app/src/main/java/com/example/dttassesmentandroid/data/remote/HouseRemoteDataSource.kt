package com.example.dttassesmentandroid.data.remote

import com.example.dttassesmentandroid.data.model.toHouse
import com.example.dttassesmentandroid.domain.model.House
import com.example.dttassesmentandroid.network.HouseApiService
import com.example.dttassesmentandroid.network.NetworkResult
import javax.inject.Inject

/**
 * Fetches data from remote source, calling the generic safeApiCall function.
 */
class HouseRemoteDataSource @Inject constructor(
    private val houseApiService: HouseApiService
) : BaseAPIResponse() {
    suspend fun fetchHouses(): NetworkResult<List<House>> {
        return safeApiCall(apiCall = { houseApiService.getAllHouses() },
            transform = { housesResponse -> housesResponse.map { it.toHouse() } })
    }
}

