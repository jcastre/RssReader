package com.jcastrejon.rssreader.domain.usecases

import com.jcastrejon.rssreader.domain.repository.Repository

/**
 * Get an item of the feed
 */
class GetFeedItemUseCase(private val feedRepository: Repository) {

    operator fun invoke(itemId: Int) = feedRepository.getFeedItem(itemId)
}