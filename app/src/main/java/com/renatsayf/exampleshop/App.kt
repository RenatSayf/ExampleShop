package com.renatsayf.exampleshop

import android.app.Application
import android.content.Context
import com.renatsayf.exampleshop.di.AppComponent
import com.renatsayf.exampleshop.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.create()
    }
}

val Context.appComponent: AppComponent
    get() {
        return when(this) {
            is App -> appComponent
            else -> this.applicationContext.appComponent
        }
    }