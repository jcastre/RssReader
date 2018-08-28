package com.jcastrejon.rssreader.data.models

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "channel", strict = false)
data class Channel(@field:ElementList(entry = "item", inline = true)
                   @param:ElementList(entry = "item", inline = true)
                   val items: List<RSSFeedItem>? = null)