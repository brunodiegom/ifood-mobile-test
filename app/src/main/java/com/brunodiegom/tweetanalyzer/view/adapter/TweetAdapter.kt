package com.brunodiegom.tweetanalyzer.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.brunodiegom.tweetanalyzer.component.AdapterItemsContract
import com.brunodiegom.tweetanalyzer.databinding.TweetItemBinding
import com.brunodiegom.tweetanalyzer.model.Tweet

/**
 * Tweet adapter is responsible to show tweet itself.
 */
class TweetAdapter(var tweets: List<Tweet>) : RecyclerView.Adapter<TweetAdapter.ViewHolder>(), AdapterItemsContract {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: TweetItemBinding = TweetItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tweets.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tweets[position])
    }

    override fun replaceItems(list: List<*>) {
        tweets = list as List<Tweet>
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: TweetItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(tweet: Tweet) {
            binding.tweet = tweet
            binding.executePendingBindings()
        }
    }
}
