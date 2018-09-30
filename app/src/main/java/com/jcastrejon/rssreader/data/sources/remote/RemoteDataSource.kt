package com.jcastrejon.rssreader.data.sources.remote

import arrow.core.Either
import com.jcastrejon.rssreader.utils.BASE_URL
import com.jcastrejon.rssreader.data.models.RSSFeed
import com.jcastrejon.rssreader.data.sources.DataSource
import com.jcastrejon.rssreader.data.sources.remote.mapper.Mapper
import com.jcastrejon.rssreader.domain.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.io.IOException

/**
 * Implementation of the data source for remote operations
 */
class RemoteDataSource(private val mapper: Mapper<RSSFeed>) : DataSource {

    private val apiService: RemoteApiService

    init {

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()

        apiService = retrofit.create(RemoteApiService::class.java)
    }

    override fun getFeed(): Either<DomainError, List<FeedItem>> = try {
        val response = apiService.getMovies().execute()

        if (response.isSuccessful && response.body() != null) {
            val feed = response.body()

            if (feed != null) {
                Either.right(mapper.map(feed))
            } else {
                Either.right(listOf())
            }
        } else {
            Either.left(UnknownError)
        }
    } catch (e: IOException) {
        Either.left(UnknownError)
    }
}