package com.brunodiegom.tweetanalyzer.view.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.brunodiegom.tweetanalyzer.R
import com.brunodiegom.tweetanalyzer.component.Logger
import com.brunodiegom.tweetanalyzer.databinding.ActivityTimelineBinding
import com.brunodiegom.tweetanalyzer.service.APIManager
import com.brunodiegom.tweetanalyzer.view.adapter.TweetAdapter
import com.brunodiegom.tweetanalyzer.viewmodel.TimelineViewModel
import org.koin.android.ext.android.inject

/**
 * Activity to show the tweet list.
 * - Create the DataBinding object based on the layout.
 * - Get the screen name from the intent.
 * - Set the timeline to the ViewModel object.
 * - Set the view model to the binding object.
 * - Load Twitter data based on screen name.
 */
class TimelineActivity : AppCompatActivity() {

    /**
     * Received screen name to be used on search.
     */
    private var screenName: String = ""
    /**
     * Injection of [APIManager].
     */
    private val apiManager: APIManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityTimelineBinding = DataBindingUtil.setContentView(this, R.layout.activity_timeline)

        screenName = intent.getStringExtra(SCREEN_NAME) ?: ""
        Log.d(TAG, "Screen Name: $screenName")

        binding.timelineViewModel = getViewModel()
        binding.tweetList.adapter = TweetAdapter(emptyList())
    }

    /**
     * Get [TimelineViewModel], loading user data.
     *
     * @return instance of [TimelineViewModel]
     */
    private fun getViewModel() = TimelineViewModel(apiManager).apply { this.load(screenName) }

    companion object {
        private val TAG = Logger.tag
        /**
         * Tag to value used on Twitter as search parameter.
         */
        const val SCREEN_NAME = "screen_name"
    }
}