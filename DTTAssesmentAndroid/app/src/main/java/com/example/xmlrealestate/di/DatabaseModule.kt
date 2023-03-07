package com.example.xmlrealestate.di

import android.content.Context
import androidx.room.Room
import com.example.xmlrealestate.common.Constants
import com.example.xmlrealestate.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

/**
 * This class is used to provide instances of the AppDatabase and HouseDao classes, which are used to access the xmlrealestate's local database.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    /**
     * Provides the location of the asset database.
     * @return a String representing the path to the asset database
     */
    @Provides
    @Named(Constants.ASSET_DB)
    fun getAssetDB(): String = Constants.DB_PATH

    /**
     * Provides an instance of the application's database.
     * @param context the application context
     * @return an instance of the AppDatabase
     */
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, Constants.HOUSES_DATABASE)
            .fallbackToDestructiveMigration()
            .build()

    /**
     * Provides an instance of the HouseDao for accessing the House data in the database.
     * @param appDatabase an instance of the AppDatabase
     * @return an instance of the HouseDao
     */
    @Provides
    fun providesHouseDao(appDatabase: AppDatabase) = appDatabase.daoHouseLocal()
}