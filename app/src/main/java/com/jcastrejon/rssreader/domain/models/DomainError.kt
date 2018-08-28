package com.jcastrejon.rssreader.domain.models

/**
 * Models for the errors
 */
sealed class DomainError
object InternetError: DomainError()
object UnknownError: DomainError()