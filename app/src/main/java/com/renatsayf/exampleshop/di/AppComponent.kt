package com.renatsayf.exampleshop.di


import android.app.Application
import com.renatsayf.exampleshop.di.modules.AppModule
import com.renatsayf.exampleshop.di.modules.NetRepositoryModule
import com.renatsayf.network.repository.NetRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Scope


@[AppScope Component(modules = [AppModule::class])]
interface AppComponent {

    val netRepository: NetRepository

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}

@Scope
annotation class AppScope