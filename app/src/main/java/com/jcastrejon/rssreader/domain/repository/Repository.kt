package com.jcastrejon.rssreader.domain.repository

import arrow.core.Either
import com.jcastrejon.rssreader.domain.models.DomainError
import com.jcastrejon.rssreader.domain.models.FeedItem
import com.jcastrejon.rssreader.domain.models.Result

/**
 * Repository definition
 */
interface Repository {

    /**
     * Get the feed of the rss
     */
    fun getFeed(): Either<DomainError, List<FeedItem>>

    /**
     * Get and item of the feed
     *
     * @param itemId, the item id
     * @param func, the callback to notify the finalization
     */
    fun getFeedItem(itemId: Int, func: (FeedItem) -> Unit)
}