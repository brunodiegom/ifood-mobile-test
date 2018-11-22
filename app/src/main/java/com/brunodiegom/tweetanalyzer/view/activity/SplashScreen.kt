package com.brunodiegom.tweetanalyzer.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.brunodiegom.tweetanalyzer.R
import com.brunodiegom.tweetanalyzer.service.TwitterAPIManager
import org.koin.android.ext.android.inject

/**
 * Loading screen shown until initialization.
 */
class SplashScreen : AppCompatActivity(), TwitterAPIManager.InitializeListener {

    /**
     * Injection of [TwitterAPIManager].
     */
    private val apiManager: TwitterAPIManager by inject()

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