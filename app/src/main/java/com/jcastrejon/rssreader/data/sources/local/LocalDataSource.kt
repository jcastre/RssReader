package com.jcastrejon.rssreader.data.sources.local

import arrow.core.Either
import com.jcastrejon.rssreader.data.sources.DataSource
import com.jcastrejon.rssreader.domain.models.DomainError
import com.jcastrejon.rssreader.domain.models.FeedItem
import com.jcastrejon.rssreader.domain.models.Result
import com.jcastrejon.rssreader.domain.models.Success
import com.jcastrejon.rssreader.utils.AppExecutors

/**
 * Implementation of a data source with local persistence
 */
class LocalDataSource(private val feedDao: FeedDao,
                      private val appExecutors: AppExecutors
): DataSource {

    override fun getFeed(): Either<DomainError, List<FeedItem>> = Either.right(feedDao.getFeed())

    override fun populateData(items: List<FeedItem>) {
        appExecutors.diskIO.execute { feedDao.populateFeed(items) }
    }
}