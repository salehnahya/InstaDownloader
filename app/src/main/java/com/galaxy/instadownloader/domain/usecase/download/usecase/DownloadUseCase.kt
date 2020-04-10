package com.galaxy.instadownloader.domain.usecase.download.usecase

import arrow.core.Either

interface DownloadUseCase {

    fun downloadPost(url: String): Either<SecurityException, Unit>
}