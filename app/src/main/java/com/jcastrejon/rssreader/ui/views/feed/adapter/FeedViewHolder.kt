package com.jcastrejon.rssreader.ui.views.feed.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.jcastrejon.rssreader.domain.models.FeedItem
import com.jcastrejon.rssreader.extensions.setImageFromUrl
import kotlinx.android.synthetic.main.view_feed_item.view.*

/**
 * ViewHolder rendering a item showed in the activity
 */
class FeedViewHolder (itemView: View, private val func: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {

    /**
     * Set all the info related to the item
     *
     * @param item
     */
    fun setItem(item: FeedItem) {
        with(itemView) {
            setOnClickListener { func(item.id) }
            item_image.setImageFromUrl(item.image)
            item_title.text = item.title
            item_description.text = item.description
        }
    }
}
