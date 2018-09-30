package com.jcastrejon.rssreader.domain.usecases

import arrow.core.Either
import arrow.core.right
import com.jcastrejon.rssreader.domain.models.DomainError
import com.jcastrejon.rssreader.domain.models.FeedItem
import com.jcastrejon.rssreader.domain.models.Filter
import com.jcastrejon.rssreader.domain.models.Result
import com.jcastrejon.rssreader.domain.repository.Repository
import com.jcastrejon.rssreader.utils.EMPTY_STRING

/**
 * Get the feed of the rss
 */
class GetFeedUseCase(private val feedRepository: Repository) {

    operator fun invoke(filter: Filter): Either<DomainError, List<FeedItem>> {
        val items = feedRepository.getFeed()

        return if (filter is Filter.Text && items is Either.Right) {
            filterResponse(items, filter.seed)
        } else {
            items
        }
    }

    /**
     * Filter the response of
     */
    private fun filterResponse(
            items: Either<DomainError, List<FeedItem>>,
            seed: String
    ): Either<DomainError, List<FeedItem>> =
            items.map { feed -> feed.filter { item -> item.title?.contains(seed, true) ?: false } }
}