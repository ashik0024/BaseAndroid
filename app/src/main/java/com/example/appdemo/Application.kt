package com.example.appdemo

import android.app.Application
import com.example.appdemo.network.retrofit.AppContext
import com.example.appdemo.network.retrofit.RetrofitClient


class DemoApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        AppContext.init(this)

    }
}