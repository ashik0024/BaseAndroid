package com.example.appdemo

import android.app.Application
import com.example.appdemo.network.retrofit.AppContext
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DemoApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        AppContext.init(this)

    }
}