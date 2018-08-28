package com.jcastrejon.rssreader.di

import com.jcastrejon.rssreader.data.sources.local.LocalDataSource
import com.jcastrejon.rssreader.data.sources.DataSource
import org.koin.dsl.module.applicationContext

val localDataSourceModule = applicationContext {

    factory { LocalDataSource() as DataSource }

}