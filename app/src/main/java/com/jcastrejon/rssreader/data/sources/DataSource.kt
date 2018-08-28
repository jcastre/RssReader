package com.jcastrejon.rssreader.data.sources

import com.jcastrejon.rssreader.domain.models.DomainError
import com.jcastrejon.rssreader.domain.models.FeedItem
import com.jcastrejon.rssreader.domain.models.Result

/**
 * Common interface for the data sources
 */
interface DataSource {

    /**
     * Request the feed
     *
     * @param func the callback to notify the finalization
     */
    fun getFeed(func: (Result<List<FeedItem>, DomainError>) -> Unit)

    /**
     * Fill the data source with new data
     */
    fun populateData(value: List<FeedItem>) { }

}