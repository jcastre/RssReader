package com.jcastrejon.rssreader.data.sources

import arrow.core.Either
import com.jcastrejon.rssreader.domain.models.DomainError
import com.jcastrejon.rssreader.domain.models.FeedItem

/**
 * Common interface for the data sources
 */
interface DataSource {

    /**
     * Request the feed
     */
    fun getFeed(): Either<DomainError, List<FeedItem>>

    /**
     * Fill the data source with new data
     *
     * @param items the items to insert
     */
    fun populateData(items: List<FeedItem>) { }

}