package com.example.appdemo.roomDb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserInfoViewModel(private val userInfoDao: UserInfoDao) : ViewModel() {

    private val _usersList = MutableLiveData<List<UserInfo>>()
    val usersList: LiveData<List<UserInfo>> get() = _usersList

    private val _playerList = MutableLiveData<List<PlayerInfo>>()
    val playerList: LiveData<List<PlayerInfo>> get() = _playerList

    init {
        getAllUsersInfo()
        getAllPlayerInfo()
    }

    // Fetch all users and update the LiveData
    fun getAllUsersInfo() {
        viewModelScope.launch {
            _usersList.value = userInfoDao.getAllUsersInfo()
        }
    }
    fun getAllPlayerInfo() {
        viewModelScope.launch {
            _playerList.value = userInfoDao.getAllPlayerInfo()
        }
    }


    // Add or update a user
    fun addOrUpdateUser(userInfo: UserInfo) {
        viewModelScope.launch {
            userInfoDao.addOrUpdateUserInfo(userInfo)
            getAllUsersInfo() // Refresh the list after an update
        }
    }
    fun removeUserInfo(userInfo: UserInfo) {
        viewModelScope.launch {
            userInfoDao.deleteUserInfo(userInfo)
            getAllUsersInfo() // Refresh the list after an update
        }
    }

    fun addOrUpdatePlayer(playerInfo: PlayerInfo) {
        viewModelScope.launch {
            userInfoDao.addOrUpdatePlayerInfo(playerInfo)
            getAllUsersInfo() // Refresh the list after an update
        }
    }
}
