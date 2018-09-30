package com.jcastrejon.rssreader.ui.presenters.feed

import arrow.core.Either
import com.jcastrejon.rssreader.R
import com.jcastrejon.rssreader.utils.EMPTY_STRING
import com.jcastrejon.rssreader.domain.models.*
import com.jcastrejon.rssreader.domain.usecases.GetFeedUseCase
import com.jcastrejon.rssreader.ui.contracts.feed.FeedContract
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext

/**
 * Presenter with the logic of the feed screen
 */
class FeedPresenter(private val view: FeedContract.View,
                    private val getFeed: GetFeedUseCase
) : FeedContract.Presenter {

    private var currentFilter: Filter = Filter.None

    override fun onResume() {
        getRssFeed()
    }

    override fun onItemClicked(id: Int) {
        view.navigateToItemDetail(id)
    }

    override fun onSearchButtonClicked(input: String) {
        getFilteredRssFeed(input)
    }

    override fun onEditorAction(input: String, isActionDone: Boolean) {
        if (isActionDone) {
            getFilteredRssFeed(input)
        }
    }

    override fun stateRecovered(filter: String?) {
        if (filter != null && filter.trim() != EMPTY_STRING) {
            showFilter(filter)
        }
    }

    override fun onRemoveButtonClicked() {
        getRssFeed()
        removeFilter()
    }

    /**
     * Request the feed
     */
    private fun getRssFeed() = GlobalScope.launch(UI) {
        showLoading()

        val result = withContext(Dispatchers.Default) { getFeed(currentFilter) }

        when (result) {
            is Either.Left -> handleErrors(result.a)
            is Either.Right -> handleSuccess(result.b)
        }
    }

    /**
     * Request the feed with a filter
     *
     * @param input, the text to filter with
     */
    private fun getFilteredRssFeed(input: String) {
        if (input.trim() != EMPTY_STRING) {
            view.closeKeyboard()
            view.clearInput()
            showFilter(input)
            getRssFeed()
        }
    }

    /**
     * Handle the success result
     *
     * @param items
     */
    private fun handleSuccess(items: List<FeedItem>) {
        when {
            items.isEmpty() -> { showMessage(R.string.empty_list_message) }
            else -> { showItems(items) }
        }
    }

    /**
     * Handle errors
     *
     * @param error
     */
    private fun handleErrors(error: DomainError) {
        when (error) {
            InternetError -> { showMessage(R.string.internetError) }
            UnknownError -> { showMessage(R.string.unknownError) }
        }
    }

    /**
     * Show the progress bar and hide the content
     */
    private fun showLoading() {
        view.hideContent()
        view.hideMessage()
        view.showProgress()
    }

    /**
     * Show the items
     *
     * @param items, the collection with the items
     */
    private fun showItems(items: List<FeedItem>) {
        view.hideProgress()
        view.showItems(items)
        view.showContent()
    }

    /**
     * Show a message instead the list of movies
     *
     * @param textRest
     */
    private fun showMessage(textRest: Int) {
        view.hideProgress()
        view.setMessage(textRest)
        view.showMessage()
    }

    /**
     * Show the filter item element and the remove button
     *
     * @param seed the text to filter the results
     */
    private fun showFilter(seed: String) {
        currentFilter = Filter.Text(seed)
        view.setFilter(seed)
        view.showFilter()
        view.showRemoveFilterButton()
    }

    /**
     * Restart the filter
     */
    private fun removeFilter() {
        currentFilter = Filter.None
        view.setFilter(EMPTY_STRING)
        view.removeFilter()
        view.hideRemoveFilterButton()
    }
}