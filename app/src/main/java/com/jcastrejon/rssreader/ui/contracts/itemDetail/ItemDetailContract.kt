package com.jcastrejon.rssreader.ui.contracts.itemDetail

import com.jcastrejon.rssreader.ui.contracts.base.BaseContract

/**
 * Contract between the item detail view and its presenter
 */
class ItemDetailContract {

    /**
     * View
     */
    interface View : BaseContract.View {

        /**
         * Show the item image
         *
         * @param image, the item image
         */
        fun showFeedImage(image: String?)

        /**
         * Show the item title
         *
         * @param title, the item title
         */
        fun showFeedTitle(title: String?)

        /**
         * Show the item description
         *
         * @param description, the item description
         */
        fun showFeedDescription(description: String?)

        /**
         * Show all the content
         */
        fun showContent()

        /**
         * Open the link in browser
         *
         * @param url the page url
         */
        fun openLinkInBrowser(url: String)
    }

    /**
     * Presenter
     */
    interface Presenter : BaseContract.Presenter {

        /**
         * The view was initialized
         *
         * @param itemId, item id to find it
         */
        fun initialize(itemId: Int)


        /**
         * Link button was clicked
         */
        fun onLinkButtonClicked()
    }
}