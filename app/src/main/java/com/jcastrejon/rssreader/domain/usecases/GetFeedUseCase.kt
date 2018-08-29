package com.jcastrejon.rssreader.domain.usecases

import com.jcastrejon.rssreader.domain.models.DomainError
import com.jcastrejon.rssreader.domain.models.FeedItem
import com.jcastrejon.rssreader.domain.models.Result
import com.jcastrejon.rssreader.domain.repository.Repository
import com.jcastrejon.rssreader.utils.EMPTY_STRING

/**
 * Get the feed of the rss
 */
class GetFeedUseCase(private val feedRepository: Repository) {

    operator fun invoke(filter: String = EMPTY_STRING, func: (Result<List<FeedItem>, DomainError>) -> Unit) {
        feedRepository.getFeed(filter, func)
    }
}