package com.jcastrejon.rssreader.ui.contracts.feed

import com.jcastrejon.rssreader.domain.models.FeedItem
import com.jcastrejon.rssreader.ui.contracts.base.BaseContract

/**
 * Contract between the Feed View and its presenter
 */
class FeedContract {

    /**
     * View
     */
    interface View : BaseContract.View {

        /**
         * Hide the the list of items
         */
        fun hideContent()

        /**
         * Show the the list of items
         */
        fun showContent()

        /**
         * Show the list of items
         *
         * @param items, the collection with the items
         */
        fun showItems(items: List<FeedItem>)

        /**
         * Set the current filter text and show it
         *
         * @param input, the text in the input
         */
        fun setFilter(input: String)

        /**
         * Show the current filter view
         */
        fun showFilter()

        /**
         * Remove the current filter view
         */
        fun removeFilter()

        /**
         * Show the remove filter button
         */
        fun showRemoveFilterButton()

        /**
         * Hide the remove filter button
         */
        fun hideRemoveFilterButton()

        /**
         * Clear the input field
         */
        fun clearInput()

        /**
         * Close the keyboard
         */
        fun closeKeyboard()

        /**
         * Navigate to the item detail screen
         *
         * @param id, the id of the item
         */
        fun navigateToItemDetail(id: Int)

        /**
         * Set the message value
         *
         * @param textRest, the string resource
         */
        fun setMessage(textRest: Int)

        /**
         * Hide the message
         */
        fun hideMessage()

        /**
         * Show the message
         */
        fun showMessage()
    }

    /**
     * Presenter
     */
    interface Presenter : BaseContract.Presenter {

        /**
         * On resume lifecycle reached
         */
        fun onResume()

        /**
         * An item of the list was clicked
         *
         * @param id, the item id
         */
        fun onItemClicked(id: Int)

        /**
         * The search button was clicked
         *
         * @param input, the value of the edit text
         */
        fun onSearchButtonClicked(input: String)

        /**
         * the remove button was clicked
         */
        fun onRemoveButtonClicked()

        /**
         * The previously saved state was recovered
         *
         * @param filter, the filter previously saved
         */
        fun stateRecovered(filter: String?)
    }
}
