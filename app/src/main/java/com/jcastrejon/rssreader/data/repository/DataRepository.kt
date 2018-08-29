package com.jcastrejon.rssreader.data.repository

import com.jcastrejon.rssreader.data.sources.DataSource
import com.jcastrejon.rssreader.domain.models.*
import com.jcastrejon.rssreader.domain.repository.Repository
import com.jcastrejon.rssreader.utils.EMPTY_STRING

/**
 * Implementation of the repository of the domain layer
 */
class DataRepository(
        private val localDataSource: DataSource,
        private val remoteDataSource: DataSource) : Repository {

    companion object {

        private const val ACCEPTABLE_TIME = 30000
    }

    private val cache: LinkedHashMap<Int, FeedItem> = LinkedHashMap()
    private var lastUpdate = 0L

    override fun getFeed(filter: String, func: (Result<List<FeedItem>, DomainError>) -> Unit) {
        if (cache.isEmpty() || !isCacheUpdated()) {
            remoteDataSource.getFeed { result -> handleGetFeedResultFromRemoteSource(filter, result, func) }
        } else {
            returnSuccessRequest(filter, func)
        }
    }

    override fun getFeedItem(itemId: Int, func: (FeedItem) -> Unit) {
        func(cache[itemId]!!)
    }

    /**
     * Handle the result of the get feed request from the remote data source
     *
     * @param filter, the filter to apply
     * @param result the result of the request
     * @param func the callback to notify the finalization
     */
    private fun handleGetFeedResultFromRemoteSource(filter: String,
                                                    result: Result<List<FeedItem>, DomainError>,
                                                    func: (Result<List<FeedItem>, DomainError>) -> Unit) {
        if (result is Success) {
            localDataSource.populateData(result.value)
            updateCache(result.value)
            returnSuccessRequest(filter, func)
        } else if (result is Error) {
            if (result.value is InternetError) {
                localDataSource.getFeed {
                    localResult -> handleGetFeedResultFromLocalSource(filter, localResult, func)
                }
            } else {
                func(result)
            }
        }
    }

    /**
     * Handle the result of the get feed request from the local data source
     *
     * @param filter, the filter to apply
     * @param result the result of the request
     * @param func the callback to notify the finalization
     */
    private fun handleGetFeedResultFromLocalSource(filter: String,
                                                   result: Result<List<FeedItem>, DomainError>,
                                                   func: (Result<List<FeedItem>, DomainError>) -> Unit) {
        if (result is Success) {
            updateCache(result.value)
            returnSuccessRequest(filter, func)
        } else {
            func(result)
        }
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

    /**
     * Return the success sorting the result by date
     *
     * @param filter, the filter to apply
     * @param func, the callback to notify after sorting
     */
    private fun returnSuccessRequest(filter: String, func: (Result<List<FeedItem>, DomainError>) -> Unit) {
        val list = ArrayList(cache.values)
        list.sortByDescending { it.date }

        if (filter != EMPTY_STRING) {
            func(Success(list.filter { it.title?.contains(filter, true) ?: false }))
        } else {
            func(Success(list))
        }
    }
}