package com.brunodiegom.tweetanalyzer.component

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.squareup.picasso.Picasso

/**
 * Binder adapter used on [ImageView] to load images through [Picasso].
 */
@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String?) {
    Picasso.with(context).load(url).into(this)
}