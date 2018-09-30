package com.jcastrejon.rssreader.ui.views.itemDetail

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.Toolbar
import com.jcastrejon.rssreader.R
import com.jcastrejon.rssreader.di.Params.ITEM_DETAIL_VIEW
import com.jcastrejon.rssreader.extensions.setImageFromUrl
import com.jcastrejon.rssreader.extensions.show
import com.jcastrejon.rssreader.ui.contracts.itemDetail.ItemDetailContract
import com.jcastrejon.rssreader.ui.presenters.itemDetail.ItemDetailPresenter
import com.jcastrejon.rssreader.ui.views.base.BaseActivity
import kotlinx.android.synthetic.main.content_item_detail.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.koin.android.ext.android.inject

/**
 * Activity to show the item details
 */
class ItemDetailActivity : BaseActivity(), ItemDetailContract.View {

    companion object {

        private const val ITEM_ID_ARG = "item_id_arg"

        /**
         * Launch the item detail activity
         *
         * @param activity, the launcher activity
         * @param itemId the item id
         */
        fun startItemDetailActivity(activity: Activity, itemId: Int) {
            val intent = Intent(activity, ItemDetailActivity::class.java)
            intent.putExtra(ITEM_ID_ARG, itemId)
            activity.startActivity(intent)
        }
    }

    override val layoutId: Int = R.layout.activity_item_detail
    override val toolbarView: Toolbar
        get() = toolbar

    private val presenter: ItemDetailPresenter by inject { mapOf(ITEM_DETAIL_VIEW to this) }

    override fun initialize(state: Bundle?) {
        initializeListeners()
        presenter.initialize(intent.extras.getInt(ITEM_ID_ARG))
    }

    override fun showFeedImage(image: String?) {
        image?.let { item_image.setImageFromUrl(it) }
    }

    override fun showFeedTitle(title: String?) {
        item_title.text = title
    }

    override fun showFeedDescription(description: String?) {
        item_description.text = description
    }

    override fun showContent() {
        content.show()
    }

    override fun openLinkInBrowser(url: String) {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (exception: ActivityNotFoundException) {
            Snackbar.make(content, R.string.activity_not_found, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun close() {
        finish()
    }

    /**
     * Initialize the listeners
     */
    private fun initializeListeners() {
        item_link.setOnClickListener { presenter.onLinkButtonClicked() }
    }

}
