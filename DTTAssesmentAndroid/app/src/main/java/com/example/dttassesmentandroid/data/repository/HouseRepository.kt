package com.example.dttassesmentandroid.data.repository

import com.example.dttassesmentandroid.data.local.DaoHouseLocal
import com.example.dttassesmentandroid.data.model.toHouse
import com.example.dttassesmentandroid.data.remote.HouseRemoteDataSource
import com.example.dttassesmentandroid.domain.model.House
import com.example.dttassesmentandroid.domain.model.toHouseEntity
import com.example.dttassesmentandroid.network.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HouseRepository @Inject constructor(
    private val houseRemoteDataSource: HouseRemoteDataSource,
    private val daoHouseLocal: DaoHouseLocal
) {
    //This function is used to get all the houses from the API and save them in the local database, if the API call fails, it will get the data from the local database.
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

    //This function is used to get a house from the Local database.
    fun getHouseCached(id: Int): Flow<House> {
        return flow {
            emit(daoHouseLocal.get(id).toHouse())
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun getAllHousesCached(): NetworkResult<List<House>> =
        daoHouseLocal.getAll().let { list ->
            NetworkResult.Success(list.map { it.toHouse() })
        }
}