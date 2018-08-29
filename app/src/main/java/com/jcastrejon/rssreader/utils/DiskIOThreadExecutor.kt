package com.jcastrejon.rssreader.utils

import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Executor in charge of the background thread
 */
class DiskIOThreadExecutor : Executor {

    private val diskIO by lazy { Executors.newSingleThreadExecutor() }

    override fun execute(runnable: Runnable) { diskIO.execute(runnable) }
}