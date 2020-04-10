package com.galaxy.instadownloader.di


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.galaxy.instadownloader.ui.image.DownloaderViewModel
import com.galaxy.instadownloader.ui.initial.StartViewModel
import com.majidalfuttaim.terminal.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(StartViewModel::class)
    abstract fun bindUserViewModel(userViewModel: StartViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DownloaderViewModel::class)
    abstract fun bindUserImageDownloaderViewModel(userViewModel: DownloaderViewModel): ViewModel



    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}