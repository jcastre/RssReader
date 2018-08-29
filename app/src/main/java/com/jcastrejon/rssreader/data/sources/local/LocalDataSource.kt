package com.jcastrejon.rssreader.data.sources.local

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
                      private val appExecutors: AppExecutors): DataSource {

    override fun getFeed(func: (Result<List<FeedItem>, DomainError>) -> Unit) {
        appExecutors.diskIO.execute {
            val items = feedDao.getFeed()
            appExecutors.uiThread.execute { func(Success(items)) }
        }
    }

    override fun populateData(items: List<FeedItem>) {
        appExecutors.diskIO.execute { feedDao.populateFeed(items) }
    }
}