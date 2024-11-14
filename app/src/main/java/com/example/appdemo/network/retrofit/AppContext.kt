package com.example.appdemo.network.retrofit

import android.content.Context

object AppContext {
    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    fun getContext(): Context {
        return appContext
    }
}