package com.example.appdemo.ui.apiNonPaging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appdemo.network.responseClass.PokemonListResponse

import kotlinx.coroutines.launch
import com.example.appdemo.network.Result
import com.example.appdemo.network.repository.GetPokemonService
import com.example.appdemo.network.responseClass.Pokemon
import com.example.appdemo.roomDb.UserInfoDao

class PokemonViewModel(private val getPokemonService: GetPokemonService) : ViewModel() {

//    private val _pokemonData = MutableLiveData<Result<PokemonListResponse>>()
//    val pokemonData: LiveData<Result<PokemonListResponse>> get() = _pokemonData
    private val _pokemonData = MutableLiveData<Result<List<Pokemon>>>()
    val pokemonData: LiveData<Result<List<Pokemon>>> get() = _pokemonData

    fun fetchPokemonData() {
        viewModelScope.launch {

            _pokemonData.value = Result.Loading

            val result = getPokemonService.getPokemonData()
            _pokemonData.value = result
        }
    }

}