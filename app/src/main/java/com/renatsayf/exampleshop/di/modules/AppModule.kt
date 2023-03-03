package com.renatsayf.exampleshop.di.modules

import com.renatsayf.exampleshop.di.AppScope
import com.renatsayf.network.repository.NetRepository
import dagger.Module
import dagger.Provides


@Module
class AppModule {

    @[Provides AppScope]
    fun provideNetRepository() = NetRepository()
}