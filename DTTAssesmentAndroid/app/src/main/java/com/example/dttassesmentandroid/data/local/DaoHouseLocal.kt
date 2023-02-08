package com.example.dttassesmentandroid.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.dttassesmentandroid.data.model.HouseEntity

@Dao
interface DaoHouseLocal {
    @Query("SELECT * FROM house order by price ASC")
    suspend fun getAll(): List<HouseEntity>

    @Query("SELECT * FROM house WHERE id = :id")
    suspend fun get(id: Int): HouseEntity

    @Insert
    suspend fun insertAll(houses: List<HouseEntity>)

    @Delete
    suspend fun deleteAll(house: List<HouseEntity>)
}