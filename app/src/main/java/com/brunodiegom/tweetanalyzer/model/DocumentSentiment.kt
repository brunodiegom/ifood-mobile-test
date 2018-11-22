package com.brunodiegom.tweetanalyzer.model

import com.google.gson.annotations.SerializedName

/**
 * Retrofit document sentiment response class.
 */
class DocumentSentiment {
    @SerializedName("score")
    var score: Float? = null
}