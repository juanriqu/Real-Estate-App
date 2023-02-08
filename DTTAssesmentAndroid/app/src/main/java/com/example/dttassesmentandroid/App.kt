package com.example.dttassesmentandroid

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        //Timber initialization for logging
        Timber.plant(Timber.DebugTree())
    }
}