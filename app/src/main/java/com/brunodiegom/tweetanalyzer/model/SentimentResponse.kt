package com.brunodiegom.tweetanalyzer.model

import com.google.gson.annotations.SerializedName

/**
 * Retrofit sentiment response class.
 */
class SentimentResponse {
    @SerializedName("documentSentiment")
    var documentSentiment: DocumentSentiment? = null
}