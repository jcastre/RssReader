package com.jcastrejon.rssreader.di

import com.jcastrejon.rssreader.di.Params.FEED_VIEW
import com.jcastrejon.rssreader.ui.presenters.feed.FeedPresenter
import org.koin.dsl.module.applicationContext

val appModule = applicationContext {

    factory { params -> FeedPresenter(params[FEED_VIEW]) }
}

object Params {

    const val FEED_VIEW = "FEED_VIEW"

}