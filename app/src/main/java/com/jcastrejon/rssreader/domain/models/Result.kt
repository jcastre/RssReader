package com.jcastrejon.rssreader.domain.models

/**
 * Result which will contains either a success model or a error model
 */
sealed class Result<out S, out E>
data class Success<out S>(val value: S) : Result<S, Nothing>()
data class Error<out E>(val value: E) : Result<Nothing, E>()