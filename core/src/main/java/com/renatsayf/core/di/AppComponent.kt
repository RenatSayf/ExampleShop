package com.renatsayf.core.di

import com.renatsayf.core.di.modules.NetRepositoryModule
import com.renatsayf.network.repository.NetRepository
import dagger.Component


@Component(modules = [NetRepositoryModule::class])
interface AppComponent {


    val netRepository: NetRepository
}