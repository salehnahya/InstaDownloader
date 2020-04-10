package com.galaxy.instadownloader.domain.usecase.download.usecase

import arrow.core.Either
import com.danielecampogiani.instatools.download.usecase.chooseFileName
import com.danielecampogiani.instatools.download.usecase.getAndEventuallyCreateDirectory
import javax.inject.Inject

class DownloadUseCaseImpl @Inject constructor(private val downloader: Downloader) :
    DownloadUseCase {

    override fun downloadPost(url: String): Either<SecurityException, Unit> {
        val filename = chooseFileName(url)
        val directory = getAndEventuallyCreateDirectory()
        return downloader.download(filename.orEmpty(), directory, url)
    }
}