package com.jcastrejon.rssreader.data.models

import org.simpleframework.xml.*

/**
 * Model for the feed of the data layer
 */
@Root(name = "rss", strict = false)
data class RSSFeed(@field:Element(name = "channel")
                   @param:Element(name = "channel")
                   val channel: Channel? = null)
