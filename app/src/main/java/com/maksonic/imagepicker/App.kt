package com.maksonic.imagepicker

import android.app.Application
import com.maksonic.imagepicker.di.navigationModule
import com.maksonic.imagepicker.feature_picker.di.featureModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * @Author: maksonic on 23.12.2021
 */
class App : Application() {
    private val modules = listOf(navigationModule, featureModule)
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(modules)
        }
    }
}