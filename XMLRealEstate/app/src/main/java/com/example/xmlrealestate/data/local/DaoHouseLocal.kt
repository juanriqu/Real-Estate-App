package com.example.xmlrealestate.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.xmlrealestate.common.Constants
import com.example.xmlrealestate.data.model.HouseEntity

/**
 * Data Access Object (DAO) for [HouseEntity].
 * Used to define the methods for accessing data from the SQLite database using Room persistence library.
 */
@Dao
interface DaoHouseLocal {
    @Query(Constants.QUERY_GET_ALL_HOUSES_PRICE_ASC)
    suspend fun getAll(): List<HouseEntity>

    @Query(Constants.QUERY_GET_HOUSE_BY_ID)
    suspend fun get(id: Int): HouseEntity

    @Insert
    suspend fun insertAll(houses: List<HouseEntity>)

    @Delete
    suspend fun deleteAll(house: List<HouseEntity>)
}