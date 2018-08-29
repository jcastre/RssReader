package com.jcastrejon.rssreader.domain.repository

import com.jcastrejon.rssreader.domain.models.DomainError
import com.jcastrejon.rssreader.domain.models.FeedItem
import com.jcastrejon.rssreader.domain.models.Result

/**
 * Repository definition
 */
interface Repository {

    /**
     * Get the feed of the rss
     *
     * @param func, the callback to notify the finalization
     */
    fun getFeed(func: (Result<List<FeedItem>, DomainError>) -> Unit)

    /**
     * Get and item of the feed
     *
     * @param itemId, the item id
     * @param func, the callback to notify the finalization
     */
    fun getFeedItem(itemId: Int, func: (FeedItem) -> Unit)
}