package com.brunodiegom.tweetanalyzer.module

import com.brunodiegom.tweetanalyzer.service.GoogleAPIManager
import com.brunodiegom.tweetanalyzer.service.TwitterAPIManager
import org.koin.dsl.module.module

/**
 * Injection app module.
 */
object AppModule {
    val appModule = module {
        single { TwitterAPIManager(get()) }
        single { GoogleAPIManager(get()) }
    }
}