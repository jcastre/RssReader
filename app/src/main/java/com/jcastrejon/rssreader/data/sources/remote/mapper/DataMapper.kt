package com.jcastrejon.rssreader.data.sources.remote.mapper

import com.jcastrejon.rssreader.data.models.RSSFeed
import com.jcastrejon.rssreader.data.models.RSSFeedItem
import com.jcastrejon.rssreader.domain.models.FeedItem
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import org.xmlpull.v1.XmlPullParserException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Implementation of the mapper to map RSSFeed
 */
class DataMapper: Mapper<RSSFeed> {

    companion object {
        private const val IMG_TAG = "img"

        private const val SRC_TAG = "src"

        private const val DATE_FORMAT = "EEE, d MMM yyyy HH:mm:ss Z"

        private const val PARAGRAPH = "p"

        private const val BREAK_LINE = "\n"
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
        return FeedItem(id, item.title, getImageFromDescription(item.description), parseDescription(item.description), item.link, parseDate(item.pubDate))
    }

    /**
     * parse the date
     *
     * @param pubDate, the date in the original format
     */
    private fun parseDate(pubDate: String?): String? {
        var date: String? = null

        if (pubDate != null) {
            val format = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

            try {
                date = format.parse(pubDate).time.toString()
            } catch (e: ParseException){ }
        }

        return date
    }

    /**
     * parse the description field and return the first image that it found
     *
     * @param description the data description text
     */
    private fun getImageFromDescription(description: String?): String? {
        var image: String? = null
        var found = false

        if (description != null) {
            val factory = XmlPullParserFactory.newInstance().apply { isNamespaceAware = true }
            val xpp = factory.newPullParser().apply { setInput(StringReader(description)) }
            var eventType = xpp.eventType

            while (eventType != XmlPullParser.END_DOCUMENT && !found) {
                if (eventType == XmlPullParser.START_TAG && xpp.name == IMG_TAG) {
                    image = xpp.getAttributeValue(null, SRC_TAG)
                    found = true
                }

                try {
                    eventType = xpp.next()
                } catch (e: XmlPullParserException) { }
            }
        }

        return image
    }

    /**
     * parse the description field
     *
     * @param description the data description text
     */
    private fun parseDescription(description: String?): String {
        var desc = String()

        if (description != null) {
            val factory = XmlPullParserFactory.newInstance().apply { isNamespaceAware = true }
            val xpp = factory.newPullParser().apply { setInput(StringReader(description)) }
            var eventType = xpp.eventType

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.TEXT && xpp.text != null) {
                    desc += xpp.text.trim()

                    if (xpp.text.contains(BREAK_LINE)) {
                        desc += BREAK_LINE
                    }
                } else if (eventType == XmlPullParser.END_TAG && xpp.name == PARAGRAPH) {
                    desc += BREAK_LINE
                }

                try {
                    eventType = xpp.next()
                } catch (e: XmlPullParserException) { }
            }
        }

        return desc.trim()
    }
}