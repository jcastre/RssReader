package com.jcastrejon.rssreader.ui.presenters.itemDetail

import arrow.core.Either
import com.jcastrejon.rssreader.domain.models.FeedItem
import com.jcastrejon.rssreader.domain.usecases.GetFeedItemUseCase
import com.jcastrejon.rssreader.ui.contracts.itemDetail.ItemDetailContract
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext

/**
 * Presenter with the logic of the item detail screen
 */
class ItemDetailPresenter(private val view: ItemDetailContract.View,
                          val getFeedItem: GetFeedItemUseCase) : ItemDetailContract.Presenter {

    private var feedItem: FeedItem? = null

    override fun initialize(itemId: Int) {
        getRssItem(itemId)
    }

    /**
     * Get the rss items
     *
     * @param itemId, the id of the element
     */
    private fun getRssItem(itemId: Int) = GlobalScope.launch(UI) {
        view.showProgress()
        val result = withContext(Dispatchers.Default) { getFeedItem(itemId) }

        when (result) {
            is Either.Right -> showItem(result.b)
            is Either.Left -> view.close()
        }
    }

    override fun onLinkButtonClicked() {
        feedItem?.link?.let { view.openLinkInBrowser(it) }
    }

    /**
     * Show the item in the view
     */
    private fun showItem(feedItem: FeedItem) {
        this.feedItem = feedItem
        view.showFeedImage(feedItem.image)
        view.showFeedTitle(feedItem.title)
        view.showFeedDescription(feedItem.description)
        view.hideProgress()
        view.showContent()
    }
}