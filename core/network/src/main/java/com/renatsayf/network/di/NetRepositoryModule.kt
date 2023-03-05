package com.renatsayf.network.di

import com.renatsayf.network.data.ApiBuilder
import com.renatsayf.network.repository.INetRepository
import com.renatsayf.network.repository.NetRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object NetRepositoryModule {

    @Provides
    fun provideNetRepository(): INetRepository {
        return NetRepositoryImpl(ApiBuilder.api)
    }
}