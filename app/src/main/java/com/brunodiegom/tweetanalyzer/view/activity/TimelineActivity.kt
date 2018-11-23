package com.brunodiegom.tweetanalyzer.view.activity

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.brunodiegom.tweetanalyzer.R
import com.brunodiegom.tweetanalyzer.component.Logger
import com.brunodiegom.tweetanalyzer.databinding.ActivityTimelineBinding
import com.brunodiegom.tweetanalyzer.service.GoogleAPIManager
import com.brunodiegom.tweetanalyzer.service.TwitterAPIManager
import com.brunodiegom.tweetanalyzer.view.adapter.TweetAdapter
import com.brunodiegom.tweetanalyzer.viewmodel.TimelineViewModel
import org.koin.android.ext.android.inject
import android.support.v7.widget.DividerItemDecoration
import android.view.View
import com.brunodiegom.tweetanalyzer.component.formatUsername
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_timeline.*


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
     * Injection of [TwitterAPIManager].
     */
    private val twitterAPIManager: TwitterAPIManager by inject()
    private val googleAPIManager: GoogleAPIManager by inject()

    private var viewModel = getViewModel()

    private var binding: ActivityTimelineBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_timeline)

        screenName = intent.getStringExtra(SCREEN_NAME) ?: ""
        Log.d(TAG, "Screen Name: $screenName")
        username_input_tl.setText(screenName)


        viewModel.load(screenName)
        binding?.apply {
            timelineViewModel = viewModel
            tweetList.addItemDecoration(DividerItemDecoration(this@TimelineActivity, DividerItemDecoration.VERTICAL))
            tweetList.adapter = TweetAdapter(emptyList(), googleAPIManager.api)
            executePendingBindings()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadingVisibility.set(false)
        username_input_layout_tl.error = ""
    }

    fun reloadUser(view: View) {
        username_input_layout_tl.error = ""
        val input = username_input_tl.text.toString().formatUsername()
        if (input.isNotEmpty()) {
            if (input != screenName) {
                clean()
                viewModel.load(input)
                screenName = input
            }
        } else {
            username_input_layout_tl.error = getString(R.string.insert_valid_username)
        }
        binding?.executePendingBindings()
    }

    private fun clean() {
        binding?.apply {
            viewModel = getViewModel()
            timelineViewModel = viewModel
            executePendingBindings()
        }
    }

    /**
     * Get [TimelineViewModel], loading user data.
     *
     * @return instance of [TimelineViewModel]
     */
    private fun getViewModel() = TimelineViewModel(twitterAPIManager.api)

    companion object {
        private val TAG = Logger.tag
        /**
         * Tag to value used on Twitter as search parameter.
         */
        const val SCREEN_NAME = "screen_name"
    }
}