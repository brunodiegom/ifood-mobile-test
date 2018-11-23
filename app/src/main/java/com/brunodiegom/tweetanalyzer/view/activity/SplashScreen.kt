package com.brunodiegom.tweetanalyzer.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.brunodiegom.tweetanalyzer.R
import com.brunodiegom.tweetanalyzer.service.TwitterAPIManager
import kotlinx.android.synthetic.main.activity_splash_screen.*
import org.koin.android.ext.android.inject
import android.net.ConnectivityManager
import com.brunodiegom.tweetanalyzer.component.Logger


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

    override fun onResume() {
        super.onResume()
        if (apiManager.isInitialized) {
            init()
        }
    }

    override fun onInitialize(success: Boolean) {
        if (success) {
            init()
        } else {
            setFailure()
        }
    }

    fun retryConnection(view: View) {
        progress_bar.visibility = VISIBLE
        error_message.visibility = GONE
        retry_button.visibility = GONE
        apiManager.retryCreateClient()
    }

    private fun setFailure() {
        progress_bar.visibility = GONE
        error_message.apply {
            visibility = VISIBLE
            setText(R.string.error_connecting_server)
            setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_error, 0, 0, 0)
        }
        retry_button.visibility = VISIBLE
    }

    /**
     * Starts the [MainActivity]
     */
    private fun init() {
        Log.d(TAG, "init")
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private val TAG = Logger.tag
    }
}