package com.jcastrejon.rssreader.domain.usecases

import com.jcastrejon.rssreader.domain.models.DomainError
import com.jcastrejon.rssreader.domain.models.FeedItem
import com.jcastrejon.rssreader.domain.models.Result
import com.jcastrejon.rssreader.domain.repository.Repository

/**
 * Get the feed of the rss
 */
class GetFeedUseCase(private val feedRepository: Repository) {

    operator fun invoke(func: (Result<List<FeedItem>, DomainError>) -> Unit) {
        feedRepository.getFeed(func)
    }
}