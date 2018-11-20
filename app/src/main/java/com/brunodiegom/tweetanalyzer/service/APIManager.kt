package com.brunodiegom.tweetanalyzer.service

import android.content.Context
import android.util.Log
import com.brunodiegom.tweetanalyzer.component.Logger
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Manager class responsible to control api access.
 */
class APIManager(context: Context) {

    /**
     * Instance of [APIClient] used to request data.
     */
    var twitterApi: APIClient = createTwitterApi()

    /**
     * Controls [APIClient] initialization.
     */
    var isInitialized = false

    /**
     * [OAuthToken] instance used on server validation.
     */
    private var token: OAuthToken? = null
    /**
     * Credentials created in case of null [token].
     */
    private val credentials = Credentials.basic(CONSUMER_KEY, CONSUMER_SECRET)

    /**
     * Notifies [APIManager] initialization.
     */
    var listener: InitializeListener? = null

    /**
     * [OAuthToken] request callback.
     */
    private var tokenCallback: Callback<OAuthToken> = object : Callback<OAuthToken> {
        override fun onResponse(call: Call<OAuthToken>, response: Response<OAuthToken>) {
            Log.d(TAG, "tokenCallback - onResponse: ${response.isSuccessful}")
            if (response.isSuccessful) {
                token = response.body()
                isInitialized = true
            } else {
                Log.d(TAG, "Code: " + response.code() + "Message: " + response.message())
            }
            listener?.onInitialize(response.isSuccessful)
        }

        override fun onFailure(call: Call<OAuthToken>, t: Throwable) {
            Log.d(TAG, "tokenCallback - onFailure")
            t.printStackTrace()
            listener?.onInitialize(false)
        }
    }

    init {
        getToken()
    }

    /**
     * Creates instance of [APIClient].
     *
     * @return [APIClient] authenticated.
     */
    private fun createTwitterApi(): APIClient {
        val okHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
            val originalRequest = chain.request()

            Log.d(TAG, "Valid token: ${token?.authorization}")

            val builder = originalRequest.newBuilder().header(
                "Authorization",
                token?.authorization ?: credentials
            )

            val newRequest = builder.build()
            chain.proceed(newRequest)
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(APIClient.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(APIClient::class.java)
    }

    /**
     * Request for Authentication token
     */
    private fun getToken() {
        twitterApi.postCredentials("client_credentials").enqueue(tokenCallback)
    }

    /**
     * Listener interface used to notify initialization.
     */
    interface InitializeListener {
        fun onInitialize(success: Boolean)
    }

    companion object {
        private val TAG = Logger.tag
        /**
         * Generated keys from https://apps.twitter.com/
         */
        const val CONSUMER_KEY = "fu82oNOEcg5Q9v2PsNTCXHQTm"
        const val CONSUMER_SECRET = "mWy2WN3etMLTkp0ifNuhuFH9UqHkadxSJWRhsA3CgVCVExlP9L"
    }
}