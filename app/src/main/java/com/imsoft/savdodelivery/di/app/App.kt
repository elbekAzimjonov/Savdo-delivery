package com.imsoft.savdodelivery.di.app

import android.app.Application
import com.imsoft.savdodelivery.di.components.ApplicationComponent
import com.imsoft.savdodelivery.presentation.di.components.DaggerApplicationComponent
import com.imsoft.savdodelivery.di.module.NetworkModule

//App
class App : Application() {
    lateinit var applicationComponent: ApplicationComponent
    override fun onCreate() {
        super.onCreate()
        applicationComponent =
            DaggerApplicationComponent
                .builder()
                .networkModule(NetworkModule())
                .build()
    }
}