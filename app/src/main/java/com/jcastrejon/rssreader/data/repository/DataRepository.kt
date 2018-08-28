package com.jcastrejon.rssreader.data.repository

import com.jcastrejon.rssreader.data.sources.DataSource
import com.jcastrejon.rssreader.domain.models.*
import com.jcastrejon.rssreader.domain.repository.Repository

/**
 * Implementation of the repository of the domain layer
 */
class DataRepository(
        private val localDataSouce: DataSource,
        private val remoteDataSource: DataSource) : Repository {

    companion object {

        private const val ACCEPTABLE_TIME = 30000
    }

    private val cache: LinkedHashMap<Int, FeedItem> = LinkedHashMap()
    private var lastUpdate = 0L

    override fun getFeed(func: (Result<List<FeedItem>, DomainError>) -> Unit) {
        if (cache.isEmpty() || !isCacheUpdated()) {
            remoteDataSource.getFeed { result -> handleGetFeedResult(result, func) }
        } else {
            func(Success(ArrayList(cache.values)))
        }
    }

    /**
     * Handle the result of the get feed request
     *
     * @param result the result of the request
     * @param func the callback to notify the finalization
     */
    private fun handleGetFeedResult(result: Result<List<FeedItem>, DomainError>,
                                    func: (Result<List<FeedItem>, DomainError>) -> Unit) {
        if (result is Success) {
            updateCache(result.value)
            localDataSouce.populateData(result.value)
        }
        func(result)
    }

    /**
     * Update the cache
     *
     * @param items
     */
    private fun updateCache(items: List<FeedItem>) {
        cache.clear()
        cache.putAll(items.map { item -> item.id to item })
        lastUpdate = System.currentTimeMillis()
    }

    /**
     * Establish if the cache is updated
     */
    private fun isCacheUpdated() = (System.currentTimeMillis() - lastUpdate) < ACCEPTABLE_TIME

}