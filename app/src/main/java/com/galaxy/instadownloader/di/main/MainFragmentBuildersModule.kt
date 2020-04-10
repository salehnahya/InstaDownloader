package com.galaxy.instadownloader.di.main


import com.galaxy.instadownloader.ui.image.ImageDownloaderFragment
import com.galaxy.instadownloader.ui.initial.StartFragment
import com.galaxy.instadownloader.ui.video.VideoDownloaderFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class MainFragmentBuildersModule {


    @ContributesAndroidInjector
    abstract fun contributeStartFragment(): StartFragment

    @ContributesAndroidInjector
    abstract fun contributeVideoDownloaderFragment(): VideoDownloaderFragment

    @ContributesAndroidInjector
    abstract fun contributeImageDownloaderFragment(): ImageDownloaderFragment
}