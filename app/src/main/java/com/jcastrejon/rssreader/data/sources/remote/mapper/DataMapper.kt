package com.jcastrejon.rssreader.data.sources.remote.mapper

import com.jcastrejon.rssreader.data.models.RSSFeed
import com.jcastrejon.rssreader.data.models.RSSFeedItem
import com.jcastrejon.rssreader.domain.models.FeedItem
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import org.xmlpull.v1.XmlPullParserException

/**
 * Implementation of the mapper to map RSSFeed
 */
class DataMapper: Mapper<RSSFeed> {

    companion object {
        private const val IMG_TAG = "img"

        private const val SRC_TAG = "src"
    }

    override fun map(element: RSSFeed): List<FeedItem> {
        val list = mutableListOf<FeedItem>()

        if (element.channel?.items != null) {
            element.channel.items.forEachIndexed { index, item -> list.add(createItem(index, item)) }
        }

        return list
    }

    /**
     * Create and item with the necessary attributes
     *
     * @param id, the id of the new item
     * @param item, the data item
     */
    private fun createItem(id: Int, item: RSSFeedItem): FeedItem {
        val tuple = parseDescription(item.description)

        return FeedItem(id, item.title, tuple.first, tuple.second, item.link)
    }

    /**
     * parse the description field and return the image and the description
     *
     * @param description the data description text
     */
    private fun parseDescription(description: String?):Pair<String?, String?> {
        var image: String? = null
        var desc = String()
        var found = false
        var finish = false

        if (description != null) {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val xpp = factory.newPullParser()

            xpp.setInput(StringReader(description))
            var eventType = xpp.eventType

            while (eventType != XmlPullParser.END_DOCUMENT && !finish) {
                if (eventType == XmlPullParser.START_TAG && xpp.name == IMG_TAG && !found) {
                    image = xpp.getAttributeValue(null, SRC_TAG)
                    found = true
                } else if (eventType == XmlPullParser.TEXT) {
                    desc += xpp.text
                }

                try {
                    eventType = xpp.next()
                } catch (e: XmlPullParserException) {
                    finish = true
                }
            }
        }

        return Pair(image, desc)
    }
}