package com.jcastrejon.rssreader.ui.views.feed

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.jcastrejon.rssreader.R
import com.jcastrejon.rssreader.di.Params.FEED_VIEW
import com.jcastrejon.rssreader.domain.models.FeedItem
import com.jcastrejon.rssreader.extensions.hide
import com.jcastrejon.rssreader.extensions.hideKeyboard
import com.jcastrejon.rssreader.extensions.show
import com.jcastrejon.rssreader.ui.contracts.feed.FeedContract
import com.jcastrejon.rssreader.ui.presenters.feed.FeedPresenter
import com.jcastrejon.rssreader.ui.views.base.BaseActivity
import com.jcastrejon.rssreader.ui.views.feed.adapter.FeedAdapter
import com.jcastrejon.rssreader.ui.views.itemDetail.ItemDetailActivity
import kotlinx.android.synthetic.main.content_movies.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.koin.android.ext.android.inject

/**
 * Activity in which a list of rss items are shown
 */
class FeedActivity : BaseActivity(), FeedContract.View {

    companion object {

        private const val CURRENT_FILTER = "CURRENT_FILTER"
    }

    override val layoutId: Int = R.layout.activity_feed
    override val toolbarView: Toolbar
        get() = toolbar

    private lateinit var adapter: FeedAdapter
    private val presenter: FeedPresenter by inject { mapOf(FEED_VIEW to this) }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(CURRENT_FILTER, filter.text.toString())
        super.onSaveInstanceState(outState)

    }

    override fun initialize(state: Bundle?) {
        initializeAdapter()
        initializeRecyclerView()
        initializeListeners()
        state?.let { presenter.stateRecovered(state.getString(CURRENT_FILTER)) }
    }

    override fun hideContent() {
        recycler_view.hide()
    }

    override fun showContent() {
        recycler_view.show()
    }

    override fun showItems(items: List<FeedItem>) {
        adapter.clear()
        adapter.addAll(items)
        adapter.notifyDataSetChanged()
    }

    override fun setFilter(input: String) {
        filter.text = input
    }

    override fun showFilter() {
        filter.show()
    }

    override fun removeFilter() {
        filter.hide()
    }

    override fun showRemoveFilterButton() {
        remove_button.show()
    }

    override fun hideRemoveFilterButton() {
        remove_button.hide()
    }

    override fun clearInput() {
        input.setText("")
    }

    override fun closeKeyboard() {
        hideKeyboard()
    }

    override fun navigateToItemDetail(id: Int) {
        ItemDetailActivity.startItemDetailActivity(activity = this, itemId = id)
    }

    override fun setMessage(textRest: Int) {
        message.setText(textRest)
    }

    override fun hideMessage() {
        message.hide()
    }

    override fun showMessage() {
        message.show()
    }

    /**
     * Initialize the adapter
     */
    private fun initializeAdapter() {
        adapter = FeedAdapter { id -> presenter.onItemClicked(id) }
    }

    /**
     * Initialize the recyclerView
     */
    private fun initializeRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = adapter
    }

    /**
     * Initialize the listeners
     */
    private fun initializeListeners() {
        search_button.setOnClickListener { presenter.onSearchButtonClicked(input.text.toString()) }
        remove_button.setOnClickListener { presenter.onRemoveButtonClicked() }
        input.setOnEditorActionListener {
            _, actionId: Int, _ ->
            presenter.onEditorAction(input.text.toString(), actionId == EditorInfo.IME_ACTION_DONE)
            actionId == EditorInfo.IME_ACTION_DONE
        }
    }
}
