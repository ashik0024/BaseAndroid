package com.example.appdemo.ui.apiPaging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appdemo.network.repository.GetPokemonPagingService
import com.example.appdemo.network.repository.GetPokemonService

class PokemonPagingViewModelFactory(private val getPokemonService: GetPokemonPagingService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonDataPagingViewModel::class.java)) {
            return PokemonDataPagingViewModel(getPokemonService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}