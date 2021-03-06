package com.galaxy.instadownloader.domain.usecase.download.usecase

import arrow.core.Either

interface Downloader {

    fun download(fileName: String, directory: String, url: String): Either<SecurityException, Unit>
}