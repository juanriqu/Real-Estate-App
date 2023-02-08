package com.example.dttassesmentandroid.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dttassesmentandroid.data.model.HouseEntity

//This class is used to create the database and the DAOs. It is also used to define the entities.
@Database(
    entities = [HouseEntity::class],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun daoHouseLocal(): DaoHouseLocal
}