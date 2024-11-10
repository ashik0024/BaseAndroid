package com.example.appdemo.common

import android.view.View

interface ProviderIconCallback<T: Any>: BaseListItemCallback<T> {
    fun onProviderIconClicked(item: T){}
    fun onSubscribeButtonClicked(view: View, item: T) {}
}