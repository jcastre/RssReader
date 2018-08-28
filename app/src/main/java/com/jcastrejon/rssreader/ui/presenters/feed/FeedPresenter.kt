package com.jcastrejon.rssreader.ui.presenters.feed

import com.jcastrejon.rssreader.common.EMPTY_STRING
import com.jcastrejon.rssreader.domain.models.FeedItem
import com.jcastrejon.rssreader.ui.contracts.feed.FeedContract

/**
 * Presenter with the logic of the feed screen
 */
class FeedPresenter(private val view: FeedContract.View) : FeedContract.Presenter {

    override fun onResume() {
        showLoading()
        showMocks()
    }

    override fun onItemClicked(itemPosition: Int) {
        view.navigateToItemDetail(itemPosition)
    }

    override fun onSearchButtonClicked(input: String) {
        if (input.trim() != EMPTY_STRING) {
            //todo
            //filter use case
            view.closeKeyboard()
            showFilter(input)
            view.clearInput()
        }
    }

    override fun stateRecovered(filter: String?) {
        if (filter != null && filter.trim() != EMPTY_STRING) {
            showFilter(filter)
        }
    }

    override fun onRemoveButtonClicked() {
        //todo
        //remove filter use case
        view.removeFilter()
        view.hideRemoveFilterButton()
    }

    /**
     * Show the progress bar and hide the content
     */
    private fun showLoading() {
        view.hideContent()
        view.showProgress()
    }

    /**
     * Show the progress bar and hide the content
     */
    private fun hideLoading() {
        view.hideProgress()
        view.showContent()
    }

    /**
     * [Provisional]
     *
     * Show the mock items
     */
    private fun showMocks() {
        view.showItems(
                listOf(
                        FeedItem(0,
                                "a1",
                                "b1",
                                "http://www.gstatic.com/webp/gallery/1.jpg"),
                        FeedItem(1,
                                "a2",
                                "b2",
                                "http://www.gstatic.com/webp/gallery/1.jpg")))
        hideLoading()
    }

    /**
     * Show the filter item element and the remove button
     *
     * @param filter the text value
     */
    private fun showFilter(filter: String) {
        view.setFilter(filter)
        view.showFilter()
        view.showRemoveFilterButton()
    }
}