package com.brunodiegom.tweetanalyzer.viewmodel

import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.util.Log
import com.brunodiegom.tweetanalyzer.component.Logger
import com.brunodiegom.tweetanalyzer.model.Tweet
import com.brunodiegom.tweetanalyzer.model.User
import com.brunodiegom.tweetanalyzer.service.APIManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class TimelineViewModel(val apiManager: APIManager) {

    private var screenName: String = ""

    val user = ObservableField<User>()

    val tweets = ObservableArrayList<Tweet>()

    val loadingVisibility = ObservableBoolean(false)

    fun load(screenName: String) {
        loadingVisibility.set(true)
        this.screenName = screenName
        getUser()
        getTimeline()
    }


    /**
     * Request for user data.
     */
    private fun getUser() {
        apiManager.twitterApi.getUser(screenName).enqueue(userCallback)
    }

    /**
     * Request for Timeline.
     */
    private fun getTimeline() {
        apiManager.twitterApi.getTimeline(screenName, 30).enqueue(timelineCallback)
    }

    /**
     * [User] request callback.
     */
    private var userCallback: Callback<User> = object : Callback<User> {
        override fun onResponse(call: Call<User>, response: Response<User>) {
            Log.d(TAG, "userCallback - onResponse: ${response.isSuccessful}")
            if (response.isSuccessful) {
                user.set(response.body())
            } else {
                Log.d(TAG, "Code: " + response.code() + " Message: " + response.message())
            }
        }

        override fun onFailure(call: Call<User>, t: Throwable) {
            Log.d(TAG, "userCallback - onFailure")
            t.printStackTrace()
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
                while (iterator?.hasNext() == true) {
                    tweets.add(iterator.next())
                }
            } else {
                Log.d(TAG, "Code: " + response.code() + " Message: " + response.message())
            }
            loadingVisibility.set(false)
        }

        override fun onFailure(call: Call<ArrayList<Tweet>>, t: Throwable) {
            Log.d(TAG, "timelineCallback - onFailure")
            t.printStackTrace()
            loadingVisibility.set(false)
        }
    }

    companion object {
        private val TAG = Logger.tag
    }
}
