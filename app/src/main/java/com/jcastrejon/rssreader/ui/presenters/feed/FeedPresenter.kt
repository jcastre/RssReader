package com.jcastrejon.rssreader.ui.presenters.feed

import com.jcastrejon.rssreader.R
import com.jcastrejon.rssreader.common.EMPTY_STRING
import com.jcastrejon.rssreader.domain.models.*
import com.jcastrejon.rssreader.domain.usecases.GetFeedUseCase
import com.jcastrejon.rssreader.ui.contracts.feed.FeedContract

/**
 * Presenter with the logic of the feed screen
 */
class FeedPresenter(private val view: FeedContract.View,
                    val getFeed: GetFeedUseCase) : FeedContract.Presenter {

    override fun onResume() {
        showLoading()
        getFeed { result -> handleResult(result)}
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
        view.hideMessage()
        view.showProgress()
    }

    /**
     * Handle the result and take the corresponding action
     */
    private fun handleResult(result: Result<List<FeedItem>, DomainError>) {
        when (result) {
            is Success -> { handleSuccess(result.value) }
            is Error -> { handleErrors(result.value) }
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
     * @param filter the text value
     */
    private fun showFilter(filter: String) {
        view.setFilter(filter)
        view.showFilter()
        view.showRemoveFilterButton()
    }
}