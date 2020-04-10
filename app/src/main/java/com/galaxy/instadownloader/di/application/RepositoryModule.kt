package com.galaxy.instadownloader.di.application


import com.data.InstaApi
import com.data.InstaEndpoint
import com.data.repository.InstaRepositoryImpl
import com.galaxy.instadownloader.domain.repository.InstaRepository
import com.majidalfuttaim.terminal.data.datasource.InstaDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideInstaRepository(
        terminalApi: InstaDataSource
    ): InstaRepository {
        return InstaRepositoryImpl(terminalApi)
    }


    @Provides
    @Singleton
    fun provideInstaDatasource(endpoint: InstaEndpoint): InstaDataSource {
        return InstaApi(endpoint)
    }
}
