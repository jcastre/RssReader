package com.jcastrejon.rssreader.domain.models

sealed class Filter {
    object None: Filter()
    data class Text(val seed: String): Filter()
}