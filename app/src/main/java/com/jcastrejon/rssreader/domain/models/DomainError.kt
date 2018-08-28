package com.jcastrejon.rssreader.domain.models

/**
 * Models for the errors
 */
internal sealed class DomainError
internal object InternetError: DomainError()
internal object UnknownError: DomainError()