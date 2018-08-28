package com.jcastrejon.rssreader.data.sources.local

import com.jcastrejon.rssreader.data.sources.DataSource
import com.jcastrejon.rssreader.domain.models.DomainError
import com.jcastrejon.rssreader.domain.models.FeedItem
import com.jcastrejon.rssreader.domain.models.Result

/**
 * Implementation of a data source with local persistence
 */
class LocalDataSource: DataSource {

    override fun getFeed(func: (Result<List<FeedItem>, DomainError>) -> Unit) {

    }

    override fun populateData(value: List<FeedItem>) {

    }
}