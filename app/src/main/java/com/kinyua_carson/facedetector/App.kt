package com.kinyua_carson.facedetector

import android.app.Application
import com.kinyua_carson.facedetector.di.appModule
import com.kinyua_carson.facedetector.presentation.MainViewmodel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }
}
