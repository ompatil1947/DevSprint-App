package com.example.robodate

import android.app.Application
import timber.log.Timber

class RoboDateApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
