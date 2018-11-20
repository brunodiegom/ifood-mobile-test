package com.brunodiegom.tweetanalyzer

import android.app.Application
import android.util.Log
import com.brunodiegom.tweetanalyzer.component.Logger
import com.brunodiegom.tweetanalyzer.module.AppModule
import org.koin.android.ext.android.startKoin

/**
 * Application initializer.
 */
class InitApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
        startKoin(this, listOf(AppModule.appModule))
    }

    companion object {
        private val TAG = Logger.tag
    }
}