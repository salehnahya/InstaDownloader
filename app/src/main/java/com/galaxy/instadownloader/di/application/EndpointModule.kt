package com.majidalfuttaim.terminal.di.application

import com.data.InstaEndpoint
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class EndpointModule {

  @Provides
  @Singleton
  fun provideInstaEndpoint(retrofit: Retrofit): InstaEndpoint {
    return retrofit.create(InstaEndpoint::class.java)
  }
}
