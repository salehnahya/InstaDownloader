package com.galaxy.instadownloader.di.application

import com.galaxy.instadownloader.MainActivity
import com.galaxy.instadownloader.di.main.MainFragmentBuildersModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {


    @ContributesAndroidInjector(modules = [MainFragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity?
}