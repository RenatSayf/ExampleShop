package com.renatsayf.exampleshop.di


import com.renatsayf.exampleshop.di.modules.NetRepositoryModule
import com.renatsayf.network.repository.NetRepository
import dagger.Component


@Component(modules = [NetRepositoryModule::class])
interface AppComponent {

    val netRepository: NetRepository
}