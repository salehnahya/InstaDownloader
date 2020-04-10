package com.galaxy.instadownloader.di.application

import android.app.Application
import android.content.Context
import com.danielecampogiani.instatools.download.DownloadModules
import com.galaxy.instadownloader.BaseApplication
import com.galaxy.instadownloader.di.ViewModelModule
import com.majidalfuttaim.terminal.di.application.EndpointModule
import dagger.BindsInstance
import dagger.Component
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,NetworkModule::class, RepositoryModule::class,
        EndpointModule::class, ViewModelModule::class, DownloadModules::class, ActivityBuildersModule::class]
)
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}
