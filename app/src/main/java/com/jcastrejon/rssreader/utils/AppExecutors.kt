package com.jcastrejon.rssreader.utils

import java.util.concurrent.Executor

/**
 * Pool of executors for the app
 */
class AppExecutors (val diskIO: Executor = DiskIOThreadExecutor(),
                    val uiThread: Executor = UIThreadExecutor())