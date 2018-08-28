package com.jcastrejon.rssreader.data.sources.remote

import com.jcastrejon.rssreader.data.models.RSSFeed
import retrofit2.Call
import retrofit2.http.GET

/**
 * Define the possible request of the remote data source
 */
interface RemoteApiService {

    @GET("rss2.xml")
    fun getMovies(): Call<RSSFeed>
}