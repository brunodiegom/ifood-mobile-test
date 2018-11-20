package com.brunodiegom.tweetanalyzer.component

/**
 * Utils class to provide data to Log.
 */
object Logger {
    val tag: String
        get() {
            var tag = ""
            val ste = Thread.currentThread().stackTrace
            for (index in ste.indices) {
                if (ste[index].methodName == "getTag") {
                    tag = "(" + ste[index + 1].fileName + ":" + ste[index + 1].lineNumber + ")"
                }
            }
            return tag
        }
}
