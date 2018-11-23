package com.brunodiegom.tweetanalyzer.view.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brunodiegom.tweetanalyzer.R
import com.brunodiegom.tweetanalyzer.component.AdapterItemsContract
import com.brunodiegom.tweetanalyzer.component.Logger
import com.brunodiegom.tweetanalyzer.databinding.TweetItemBinding
import com.brunodiegom.tweetanalyzer.model.EncodingType
import com.brunodiegom.tweetanalyzer.model.SentimentResponse
import com.brunodiegom.tweetanalyzer.model.Tweet
import com.brunodiegom.tweetanalyzer.service.GoogleAPIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Tweet adapter is responsible to show tweet itself.
 */
class TweetAdapter(private var tweets: List<Tweet>, private val googleApi: GoogleAPIClient) :
    RecyclerView.Adapter<TweetAdapter.ViewHolder>(), AdapterItemsContract {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: TweetItemBinding = TweetItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, googleApi)
    }

    override fun getItemCount(): Int {
        return tweets.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tweets[position])
    }

    @Suppress("UNCHECKED_CAST")
    override fun replaceItems(list: List<*>) {
        tweets = list as List<Tweet>
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: TweetItemBinding, private val googleApi: GoogleAPIClient) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private var sentimentCallback: Callback<SentimentResponse> = object : Callback<SentimentResponse> {
            override fun onResponse(call: Call<SentimentResponse>, response: Response<SentimentResponse>) {
                Log.d(TAG, "sentimentCallback - onResponse: ${response.isSuccessful}")
                if (response.isSuccessful) {
                    response.body()?.let { setSentiment(it) }
                } else {
                    Log.d(TAG, "Code: " + response.code() + "Message: " + response.message())
                }
            }

            override fun onFailure(call: Call<SentimentResponse>, t: Throwable) {
                Log.d(TAG, "sentimentCallback - onFailure")
                t.printStackTrace()
            }
        }

        fun bind(tweet: Tweet) {
            binding.tweet = tweet
            binding.executePendingBindings()
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (tweets[adapterPosition].sentimentIcon == 0) {
                googleApi.analyzeSentiment(tweets[adapterPosition].text, EncodingType.PLAIN_TEXT)
                    .enqueue(sentimentCallback)
                binding.progressBar.visibility = View.VISIBLE
            }
        }

        fun setSentiment(sentiment: SentimentResponse) {
            val score = sentiment.documentSentiment?.score
            Log.d(TAG, "Score: $score")
            when {
                score == null -> tweets[adapterPosition].sentimentIcon = 0
                score > 0.25 -> tweets[adapterPosition].sentimentIcon = R.drawable.happy
                score > -0.25 -> tweets[adapterPosition].sentimentIcon = R.drawable.neutral
                else -> tweets[adapterPosition].sentimentIcon = R.drawable.sad
            }
            binding.tweet = tweets[adapterPosition]
            binding.progressBar.visibility = View.GONE
            binding.executePendingBindings()
        }
    }

    companion object {
        private val TAG = Logger.tag
    }
}
