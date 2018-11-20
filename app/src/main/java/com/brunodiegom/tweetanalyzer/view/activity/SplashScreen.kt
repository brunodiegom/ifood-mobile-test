package com.brunodiegom.tweetanalyzer.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.brunodiegom.tweetanalyzer.R
import com.brunodiegom.tweetanalyzer.service.APIManager
import org.koin.android.ext.android.inject

/**
 * Loading screen shown until [APIManager] initialization.
 */
class SplashScreen : AppCompatActivity(), APIManager.InitializeListener {

    /**
     * Injection of [APIManager].
     */
    private val apiManager: APIManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        apiManager.listener = this
    }

    override fun onInitialize(success: Boolean) {
        if (success) {
            init()
        }
    }

    /**
     * Starts the [MainActivity]
     */
    private fun init() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}