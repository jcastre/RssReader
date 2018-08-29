package com.jcastrejon.rssreader.di

import com.jcastrejon.rssreader.data.repository.DataRepository
import com.jcastrejon.rssreader.di.Params.FEED_VIEW
import com.jcastrejon.rssreader.di.Params.ITEM_DETAIL_VIEW
import com.jcastrejon.rssreader.domain.repository.Repository
import com.jcastrejon.rssreader.domain.usecases.GetFeedItemUseCase
import com.jcastrejon.rssreader.domain.usecases.GetFeedUseCase
import com.jcastrejon.rssreader.ui.presenters.feed.FeedPresenter
import com.jcastrejon.rssreader.ui.presenters.itemDetail.ItemDetailPresenter
import org.koin.dsl.module.applicationContext

val appModule = applicationContext {

    factory { params -> FeedPresenter(params[FEED_VIEW], get()) }

    factory { GetFeedUseCase(get()) }

    bean { DataRepository(get(name = "local"), get(name = "remote")) as Repository }

    factory { params -> ItemDetailPresenter(params[ITEM_DETAIL_VIEW], get()) }

    factory { GetFeedItemUseCase(get()) }
}

object Params {

    const val FEED_VIEW = "FEED_VIEW"
    const val ITEM_DETAIL_VIEW = "FEED_VIEW"

}