package com.example.appdemo.roomDb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UserInfoViewModelFactory(private val userInfoDao: UserInfoDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserInfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserInfoViewModel(userInfoDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}