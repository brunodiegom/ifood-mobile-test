package com.brunodiegom.tweetanalyzer.service

import com.brunodiegom.tweetanalyzer.model.Tweet
import com.brunodiegom.tweetanalyzer.model.User
import retrofit2.Call
import retrofit2.http.*

/**
 * Retrofit service to get Twitter data.
 */
interface APIClient {

    @FormUrlEncoded
    @POST("oauth2/token")
    fun postCredentials(@Field("grant_type") grantType: String): Call<OAuthToken>

    @GET("/1.1/users/show.json")
    fun getUser(@Query("screen_name") screenName: String): Call<User>

    @GET("1.1/statuses/user_timeline.json")
    fun getTimeline(@Query("screen_name") screenName: String, @Query("count") count: Int): Call<ArrayList<Tweet>>

    companion object {
        const val BASE_URL = "https://api.twitter.com/"
    }
}
