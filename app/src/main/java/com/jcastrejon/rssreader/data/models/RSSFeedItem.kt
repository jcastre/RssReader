package com.jcastrejon.rssreader.data.models

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
data class RSSFeedItem(@field:Element(name = "title")
                       @param:Element(name = "title")
                       val title: String? = null,
                       @field:Element(name = "description", data = true)
                       @param:Element(name = "description", data = true)
                       val description: String? = null,
                       @field:Element(name = "link")
                       @param:Element(name = "link")
                       val link: String? = null)