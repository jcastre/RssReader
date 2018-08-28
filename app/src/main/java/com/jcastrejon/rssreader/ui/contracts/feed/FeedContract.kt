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
         * @param itemPosition, the position of the item
         */
        fun navigateToItemDetail(itemPosition: Int)
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
         * @param itemPosition, the position in the adapter
         */
        fun onItemClicked(itemPosition: Int)

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
