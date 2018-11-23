package com.brunodiegom.tweetanalyzer.viewmodel

import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.util.Log
import com.brunodiegom.tweetanalyzer.R
import com.brunodiegom.tweetanalyzer.component.Logger
import com.brunodiegom.tweetanalyzer.model.Tweet
import com.brunodiegom.tweetanalyzer.model.User
import com.brunodiegom.tweetanalyzer.service.TwitterAPIClient
import kotlinx.android.synthetic.main.activity_splash_screen.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class TimelineViewModel(private val twitterAPIClient: TwitterAPIClient) {

    private var screenName: String = ""

    val user = ObservableField<User>()

    val tweets = ObservableArrayList<Tweet>()

    val errorMessage = ObservableField<Int>()

    val loadingVisibility = ObservableBoolean(false)

    fun load(screenName: String) {
        Log.d(TAG, "Loading: $screenName")
        loadingVisibility.set(true)
        this.screenName = screenName
        getUser()
    }

    /**
     * Request for user data.
     */
    private fun getUser() {
        twitterAPIClient.getUser(screenName).enqueue(userCallback)
    }

    /**
     * Request for Timeline.
     */
    private fun getTimeline() {
        twitterAPIClient.getTimeline(screenName, 30).enqueue(timelineCallback)
    }

    /**
     * [User] request callback.
     */
    private var userCallback: Callback<User> = object : Callback<User> {
        override fun onResponse(call: Call<User>, response: Response<User>) {
            Log.d(TAG, "userCallback - onResponse: ${response.isSuccessful}")
            if (response.isSuccessful) {
                user.set(response.body())
                getTimeline()
            } else {
                errorMessage.set(R.string.user_not_found)
                Log.d(TAG, "Code: " + response.code() + " Message: " + response.message())
                loadingVisibility.set(false)
            }
        }

        override fun onFailure(call: Call<User>, t: Throwable) {
            Log.d(TAG, "userCallback - onFailure")
            errorMessage.set(R.string.error_connecting_server)
            t.printStackTrace()
            loadingVisibility.set(false)
        }
    }

    /**
     * Timeline request callback.
     */
    private var timelineCallback: Callback<ArrayList<Tweet>> = object : Callback<ArrayList<Tweet>> {
        override fun onResponse(call: Call<ArrayList<Tweet>>, response: Response<ArrayList<Tweet>>) {
            Log.d(TAG, "timelineCallback - onResponse: ${response.isSuccessful}")
            if (response.isSuccessful) {
                tweets.clear()
                val iterator = response.body()?.iterator()
                if (iterator?.hasNext() == true) {
                    while (iterator.hasNext()) {
                        tweets.add(iterator.next())
                    }
                } else {
                    errorMessage.set(R.string.no_tweet_posted)
                }
            } else {
                errorMessage.set(R.string.tweets_not_found)
                Log.d(TAG, "Code: " + response.code() + " Message: " + response.message())
            }
            loadingVisibility.set(false)
        }

        override fun onFailure(call: Call<ArrayList<Tweet>>, t: Throwable) {
            Log.d(TAG, "timelineCallback - onFailure")
            t.printStackTrace()
            errorMessage.set(R.string.error_connecting_server)
            loadingVisibility.set(false)
        }
    }

    companion object {
        private val TAG = Logger.tag
    }
}
