package com.jcastrejon.rssreader.data.sources.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.jcastrejon.rssreader.domain.models.FeedItem

/**
 * The Room database for this app
 */
@Database(entities = [FeedItem::class], version = 1, exportSchema = false)
abstract class LocalDataBase : RoomDatabase() {

    abstract fun feedDao(): FeedDao

}