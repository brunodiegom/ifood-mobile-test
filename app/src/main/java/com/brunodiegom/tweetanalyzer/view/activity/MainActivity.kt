package com.brunodiegom.tweetanalyzer.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.brunodiegom.tweetanalyzer.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Activity to show search instance.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * Starts [TimelineActivity] sending the screen name to be used on search.
     *
     * @param view anchor xml view.
     */
    fun searchUser(view: View) {
        val name = formatUsername(username_input.text.toString())
        val intent = Intent(this, TimelineActivity::class.java)
        intent.apply {
            putExtra(TimelineActivity.SCREEN_NAME, name)
        }
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            search_button,
            ViewCompat.getTransitionName(search_button) ?: ""
        )
        startActivity(intent, options.toBundle())
    }

    /**
     * Format input screen name removing unnecessary whitespaces and at.
     *
     * @param value raw text from input.
     * @return formatted screen name.
     */
    private fun formatUsername(value: String) = value.replace("@", "").trim()
}
