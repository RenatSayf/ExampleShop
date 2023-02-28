package com.renatsayf.exampleshop

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.renatsayf.core.di.AppComponent
import com.renatsayf.core.di.DaggerAppComponent

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var appComponent: AppComponent
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appComponent = DaggerAppComponent.create()
    }
}



