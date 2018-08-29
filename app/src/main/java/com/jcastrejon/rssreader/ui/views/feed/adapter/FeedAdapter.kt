package com.jcastrejon.rssreader.ui.views.feed.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jcastrejon.rssreader.R
import com.jcastrejon.rssreader.domain.models.FeedItem

/**
 * Adapter to handle the list of items inside the recycler view
 */
class FeedAdapter(private val func: (Int) -> Unit): RecyclerView.Adapter<FeedViewHolder>() {

    private val items: MutableList<FeedItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_feed_item, parent, false)

        return FeedViewHolder(view, func)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.setItem(items[position])
    }

    override fun getItemCount(): Int = items.size

    /**
     * Add a collections of items
     *
     * @param collection
     */
    fun addAll(collection: Collection<FeedItem>) = items.addAll(collection)

    /**
     * Clear the adapter list of items
     */
    fun clear() = items.clear()
}
