package com.example.appdemo.ui.apiPaging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appdemo.network.repository.GetPokemonPagingService
import com.example.appdemo.network.repository.GetPokemonService
import com.example.appdemo.roomDb.UserInfoDao
import com.example.appdemo.ui.apiNonPaging.PokemonViewModel

//class PokemonPagingViewModelFactory(private val getPokemonService: GetPokemonPagingService) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(PokemonDataPagingViewModel::class.java)) {
//            return PokemonDataPagingViewModel(getPokemonService) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}

class PokemonPagingViewModelFactory(
    private val getPokemonService: GetPokemonPagingService,
    private val userInfoDao: UserInfoDao
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonDataPagingViewModel::class.java)) {
            return PokemonDataPagingViewModel(getPokemonService, userInfoDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}