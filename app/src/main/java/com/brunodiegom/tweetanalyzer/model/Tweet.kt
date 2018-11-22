package com.brunodiegom.tweetanalyzer.model

import android.support.annotation.DrawableRes
import com.brunodiegom.tweetanalyzer.R

/**
 * Class to represent a Tweet.
 */
class Tweet(val text: String, @DrawableRes var sentimentIcon: Int = R.drawable.thinking)