package com.brunodiegom.tweetanalyzer.component

/**
 * Format input screen name removing unnecessary whitespaces and at.
 *
 * @param value raw text from input.
 * @return formatted screen name.
 */
fun String.formatUsername() = this.replace("@", "").trim()