package com.maksonic.imagepicker

import android.app.Application
import com.maksonic.imagepicker.di.navigationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * @Author: maksonic on 23.12.2021
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(navigationModule)
        }
    }
}