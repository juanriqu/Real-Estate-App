package com.example.xmlrealestate.data.remote

import com.example.xmlrealestate.data.model.toHouse
import com.example.xmlrealestate.domain.model.House
import com.example.xmlrealestate.network.HouseAPIService
import com.example.xmlrealestate.network.NetworkResult
import javax.inject.Inject

/**
 * Fetches data from remote source, calling the generic [BaseAPIResponse.safeApiCall] function.
 */
class HouseRemoteDataSource @Inject constructor(
    private val houseApiService: HouseAPIService
) : BaseAPIResponse() {
    suspend fun fetchHouses(): NetworkResult<List<House>> {
        return safeApiCall(apiCall = { houseApiService.getAllHouses() },
            transform = { housesResponse -> housesResponse.map { it.toHouse() } })
    }
}

