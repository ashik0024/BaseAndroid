package com.example.appdemo.Network

sealed class Result<out T> {
    object Loading : Result<Nothing>()  // Add a loading state
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
}
