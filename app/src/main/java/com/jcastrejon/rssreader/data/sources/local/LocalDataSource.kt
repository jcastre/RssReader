package com.jcastrejon.rssreader.data.sources.local

import arrow.core.Either
import com.jcastrejon.rssreader.data.sources.DataSource
import com.jcastrejon.rssreader.domain.models.DomainError
import com.jcastrejon.rssreader.domain.models.FeedItem

/**
 * Implementation of a data source with local persistence
 */
class LocalDataSource(private val feedDao: FeedDao): DataSource {

    override fun getFeed(): Either<DomainError, List<FeedItem>> = Either.right(feedDao.getFeed())

    override fun populateData(items: List<FeedItem>) { feedDao.populateFeed(items)
    }
}