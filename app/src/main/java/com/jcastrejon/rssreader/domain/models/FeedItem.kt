package com.jcastrejon.rssreader.domain.models

/**
 * Model for all the items of the domain layer
 */
data class FeedItem(val id: Int,
                    val title: String? = null,
                    val image: String? = null,
                    val description: String? = null,
                    val link: String? = null)