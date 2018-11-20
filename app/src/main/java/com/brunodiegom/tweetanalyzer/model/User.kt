package com.brunodiegom.tweetanalyzer.model

import com.google.gson.annotations.SerializedName

/**
 * Class to represent a User.
 */
class User(
    @SerializedName("name") val name: String,
    @SerializedName("screen_name") val screenName: String,
    @SerializedName("profile_image_url_https") val photoUrl: String
)