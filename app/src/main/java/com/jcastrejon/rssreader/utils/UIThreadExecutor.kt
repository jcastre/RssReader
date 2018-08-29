package com.jcastrejon.rssreader.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor

/**
 * Executor in charge of the UI thread
 */
class UIThreadExecutor : Executor {

    private val uiThreadHandler by lazy { Handler(Looper.getMainLooper()) }

    override fun execute(runnable: Runnable) {
        uiThreadHandler.post(runnable)
    }
}