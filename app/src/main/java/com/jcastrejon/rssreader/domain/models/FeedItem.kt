package com.jcastrejon.rssreader.domain.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Model for all the items of the domain layer
 */
@Entity(tableName = "items")
data class FeedItem(@PrimaryKey
                    val id: Int,
                    val title: String? = null,
                    val image: String? = null,
                    val description: String? = null,
                    val link: String? = null,
                    val date: String? = null)