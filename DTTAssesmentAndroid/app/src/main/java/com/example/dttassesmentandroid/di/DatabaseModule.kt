package com.example.dttassesmentandroid.di

import android.content.Context
import androidx.room.Room
import com.example.dttassesmentandroid.common.Constants
import com.example.dttassesmentandroid.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Named(Constants.ASSET_DB)
    fun getAssetDB(): String = Constants.DB_PATH

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        @Named(Constants.ASSET_DB) path: String
    ): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, Constants.HOUSES_DATABASE)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun providesHouseDao(appDatabase: AppDatabase) = appDatabase.daoHouseLocal()
}