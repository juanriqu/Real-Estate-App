package com.example.xmlrealestate.data.repository

import com.example.xmlrealestate.data.local.DaoHouseLocal
import com.example.xmlrealestate.data.model.toHouse
import com.example.xmlrealestate.data.remote.HouseRemoteDataSource
import com.example.xmlrealestate.domain.model.House
import com.example.xmlrealestate.domain.model.toHouseEntity
import com.example.xmlrealestate.network.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Repository for managing data related to houses.
 */
class HouseRepository @Inject constructor(
    private val houseRemoteDataSource: HouseRemoteDataSource,
    private val daoHouseLocal: DaoHouseLocal
) {
    /**
     * This function retrieves a list of all the houses from the API and saves them to the local database.
     * If the API call fails, it returns the data from the local database. It returns a Flow of NetworkResult<List<House>>, which represents the network request result and the data if available.
     */
    fun getAllHouses(): Flow<NetworkResult<List<House>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = houseRemoteDataSource.fetchHouses()
            if (result is NetworkResult.Success) {
                result.data?.let { house ->
                    daoHouseLocal.deleteAll(house.map { it.toHouseEntity() })
                    daoHouseLocal.insertAll(house.map { it.toHouseEntity() })
                }
                emit(result)
            } else {
                emit(getAllHousesCached())
            }
        }.flowOn(Dispatchers.IO)
    }

    /**
     * This function retrieves a house from the local database by its ID. It returns a Flow of [House].
     */
    fun getHouseCached(id: Int): Flow<House> {
        return flow {
            emit(daoHouseLocal.get(id).toHouse())
        }.flowOn(Dispatchers.IO)
    }

    /**
     * This function retrieves all houses from the local database.
     */
    private suspend fun getAllHousesCached(): NetworkResult<List<House>> {
        return daoHouseLocal.getAll().let { list ->
            NetworkResult.Success(list.map { it.toHouse() })
        }
    }
}