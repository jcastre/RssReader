package com.jcastrejon.rssreader.data.sources.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.jcastrejon.rssreader.domain.models.FeedItem

@Dao
interface FeedDao {

    @Query("SELECT * FROM items")
    fun getFeed(): List<FeedItem>

    @Insert(onConflict = REPLACE)
    fun populateFeed(items: List<FeedItem>)
}