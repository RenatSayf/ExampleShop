package com.renatsayf.core.di.modules

import com.renatsayf.network.data.ApiBuilder
import com.renatsayf.network.data.IApi
import com.renatsayf.network.repository.NetRepository
import dagger.Module
import dagger.Provides


@Module
object NetRepositoryModule {

    @Provides
    fun provideNetRepository(): NetRepository {
        return NetRepository(ApiBuilder.api)
    }
}