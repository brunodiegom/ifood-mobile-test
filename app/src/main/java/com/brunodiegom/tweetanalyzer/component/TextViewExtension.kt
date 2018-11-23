package com.brunodiegom.tweetanalyzer.component

import android.databinding.BindingAdapter
import android.widget.TextView

/**
 * Binder adapter used on [TextView] to bind text resource on.
 */
@BindingAdapter("android:text")
fun TextView.setTextResource(resource: Int) {
    if (resource == 0) {
        this.text = ""
    } else {
        this.setText(resource)
    }
}