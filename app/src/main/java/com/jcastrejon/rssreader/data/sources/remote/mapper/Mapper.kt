package com.jcastrejon.rssreader.data.sources.remote.mapper

import com.jcastrejon.rssreader.domain.models.FeedItem

/**
 * Mapper in charge of transform the data models into domain ones
 */
interface Mapper<E> {

    /**
     * Map the item to the domain layer
     */
    fun map(element: E): List<FeedItem>

}