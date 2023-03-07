package com.example.xmlrealestate

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * The `App` class is the main application class for the Android xmlrealestate. It uses the Hilt dependency injection library
 * to generate the Hilt components required for dependency injection.
 */
@HiltAndroidApp
class App : Application() {
    /**
     * Overrides the [onCreate] method. Here `Timber` library is initialized and a debug tree is planted to enable logging during development.
     */
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}