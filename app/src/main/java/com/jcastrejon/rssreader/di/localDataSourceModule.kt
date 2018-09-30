package com.jcastrejon.rssreader.di

import android.arch.persistence.room.Room
import com.jcastrejon.rssreader.utils.DATABASE_NAME
import com.jcastrejon.rssreader.data.sources.local.LocalDataSource
import com.jcastrejon.rssreader.data.sources.DataSource
import com.jcastrejon.rssreader.data.sources.local.LocalDataBase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext

val localDataSourceModule = applicationContext {

    factory(name = "local") { LocalDataSource(get()) as DataSource }

    bean {
        Room.databaseBuilder(androidApplication(), LocalDataBase::class.java, DATABASE_NAME).build()
    }

    bean { get<LocalDataBase>().feedDao() }

}