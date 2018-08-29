package com.jcastrejon.rssreader.ui.contracts.base

/**
 * Base Contract Presenter - View
 */
class BaseContract {

    /**
     * Base View
     */
    interface View {

        /**
         * Show a progressbar
         */
        fun showProgress()

        /**
         * Hide a progressbar
         */
        fun hideProgress()
    }

    /**
     * Base Presenter
     */
    interface Presenter

}