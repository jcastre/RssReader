package com.jcastrejon.rssreader.ui.presenters.itemDetail

import com.jcastrejon.rssreader.domain.models.FeedItem
import com.jcastrejon.rssreader.domain.usecases.GetFeedItemUseCase
import com.jcastrejon.rssreader.ui.contracts.itemDetail.ItemDetailContract

/**
 * Presenter with the logic of the item detail screen
 */
class ItemDetailPresenter(private val view: ItemDetailContract.View,
                          val getFeedItem: GetFeedItemUseCase) : ItemDetailContract.Presenter {

    private var feedItem: FeedItem? = null

    override fun initialize(itemId: Int) {
        view.showProgress()
        getFeedItem(itemId) { feedItem -> showItem(feedItem) }
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