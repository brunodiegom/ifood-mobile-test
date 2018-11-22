package com.brunodiegom.tweetanalyzer.service

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Manager class responsible to control api access.
 */
class GoogleAPIManager(val context: Context) {

    val api: GoogleAPIClient = createGoogleApi()

    /**
     * Creates instance of [GoogleAPIClient].
     *
     * @return [GoogleAPIClient] authenticated.
     */
    private fun createGoogleApi(): GoogleAPIClient {
        val retrofit = Retrofit.Builder()
            .baseUrl(GoogleAPIClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(GoogleAPIClient::class.java)
    }
}