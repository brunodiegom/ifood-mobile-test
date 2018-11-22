package com.brunodiegom.tweetanalyzer.component

/**
 * Interface used to bind recycler view content.
 */
interface AdapterItemsContract {
    /**
     * Replace all recycler items.
     *
     * @param list object list.
     */
    fun replaceItems(list: List<*>)
}