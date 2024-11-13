package com.example.appdemo.ui.ApiNonPaging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appdemo.Network.ReponseClass.PokemonListResponse
import com.example.appdemo.Network.Repository.GetPokemonService
import kotlinx.coroutines.launch
import com.example.appdemo.Network.Result

class PokemonViewModel(private val getPokemonService: GetPokemonService) : ViewModel() {

    private val _pokemonData = MutableLiveData<Result<PokemonListResponse>>()
    val pokemonData: LiveData<Result<PokemonListResponse>> get() = _pokemonData


    fun fetchPokemonData() {
        viewModelScope.launch {

            _pokemonData.value = Result.Loading

            val result = getPokemonService.getPokemonData()
            _pokemonData.value = result
        }
    }
}