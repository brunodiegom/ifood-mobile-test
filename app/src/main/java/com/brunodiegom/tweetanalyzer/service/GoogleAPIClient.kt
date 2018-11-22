package com.brunodiegom.tweetanalyzer.service

import com.brunodiegom.tweetanalyzer.model.EncodingType
import com.brunodiegom.tweetanalyzer.model.SentimentResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Retrofit service to get Google Natural Language data.
 */
interface GoogleAPIClient {

    @FormUrlEncoded
    @POST("/v1/documents:analyzeSentiment")
    fun analyzeSentiment(@Field("document.content") content: String,
                         @Field("document.type") type: EncodingType,
                         @Field("key") api_key: String = KEY_API): Call<SentimentResponse>

    companion object {
        const val BASE_URL = "https://language.googleapis.com/"
        const val KEY_API = ""
    }
}