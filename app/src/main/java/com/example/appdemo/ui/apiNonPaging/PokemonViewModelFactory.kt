package com.example.appdemo.ui.apiNonPaging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appdemo.network.repository.GetPokemonService

class PokemonViewModelFactory(private val getPokemonService: GetPokemonService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonViewModel::class.java)) {
            return PokemonViewModel(getPokemonService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

