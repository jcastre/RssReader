package com.jcastrejon.rssreader.di

import com.jcastrejon.rssreader.data.models.RSSFeed
import com.jcastrejon.rssreader.data.sources.remote.RemoteDataSource
import com.jcastrejon.rssreader.data.sources.DataSource
import com.jcastrejon.rssreader.data.sources.remote.mapper.DataMapper
import com.jcastrejon.rssreader.data.sources.remote.mapper.Mapper
import org.koin.dsl.module.applicationContext

val remoteDataSourceModule = applicationContext {

    factory(name ="remote") { RemoteDataSource(get()) as DataSource }

    factory { DataMapper() as Mapper<RSSFeed> }

}