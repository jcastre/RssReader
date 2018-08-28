package com.jcastrejon.rssreader

import android.app.Application
import com.jcastrejon.rssreader.di.appModule
import com.jcastrejon.rssreader.di.localDataSourceModule
import com.jcastrejon.rssreader.di.remoteDataSourceModule
import org.koin.android.ext.android.startKoin

/**
 * Application class
 */
class RssReaderApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this,
                listOf(appModule, localDataSourceModule, remoteDataSourceModule))
    }
}