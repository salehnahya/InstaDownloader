package com.danielecampogiani.instatools.download

import android.app.Application
import android.app.DownloadManager
import android.content.Context
import com.galaxy.instadownloader.domain.usecase.download.usecase.DownloadUseCase
import com.galaxy.instadownloader.domain.usecase.download.usecase.DownloadUseCaseImpl
import com.galaxy.instadownloader.domain.usecase.download.usecase.Downloader
import com.galaxy.instadownloader.domain.usecase.download.usecase.DownloaderImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class DownloadModules {

    @Provides
    internal fun providesUseCase(usecase: DownloadUseCaseImpl): DownloadUseCase {
        return usecase
    }

    @Provides
    @Singleton
    internal fun provideDownloadManager(context: Application): DownloadManager {
        return context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }

    @Provides
    internal fun providesDownloader(downloader: DownloaderImpl): Downloader {
        return downloader
    }
}