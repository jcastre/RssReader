package com.jcastrejon.rssreader.data.repository

import arrow.core.Either
import com.jcastrejon.rssreader.data.sources.DataSource
import com.jcastrejon.rssreader.domain.models.*
import com.jcastrejon.rssreader.domain.repository.Repository

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

    override fun getFeed(): Either<DomainError, List<FeedItem>> =
            if (!cache.isEmpty() and isCacheUpdated()) {
                Either.right(ArrayList(cache.values))
            } else {
                val remoteResult = remoteDataSource.getFeed()

                when  {
                    remoteResult is Either.Right -> handleSuccessRemoteResult(remoteResult.b)
                    remoteResult is Either.Left && remoteResult.a is InternetError -> { handleRemoteError() }
                    else -> remoteResult
                }

            }

    /**
     * Handle internet error
     */
    private fun handleRemoteError(): Either<DomainError, List<FeedItem>> {
        val result = localDataSource.getFeed()

        if (result is Either.Right) {
            updateCache(result.b)
        }

        return result
    }

    /**
     * Handle the success remote response
     */
    private fun handleSuccessRemoteResult(items: List<FeedItem>): Either<DomainError, List<FeedItem>> {
        localDataSource.populateData(items)
        updateCache(items)

        return Either.right(items)
    }

    override fun getFeedItem(itemId: Int, func: (FeedItem) -> Unit) {
        func(cache[itemId]!!)
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