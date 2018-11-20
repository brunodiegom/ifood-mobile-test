package com.brunodiegom.tweetanalyzer.module

import com.brunodiegom.tweetanalyzer.service.APIManager
import org.koin.dsl.module.module

/**
 * Injection app module.
 */
object AppModule {
    val appModule = module {
        single { APIManager(get()) }
    }
}