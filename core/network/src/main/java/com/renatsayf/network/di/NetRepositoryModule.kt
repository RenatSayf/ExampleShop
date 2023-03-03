package com.renatsayf.network.di

import com.renatsayf.network.data.ApiBuilder
import com.renatsayf.network.data.IApi
import com.renatsayf.network.repository.NetRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object NetRepositoryModule {

    @Provides
    fun provideNetRepository(): NetRepository {
        return NetRepository(ApiBuilder.api)
    }
}