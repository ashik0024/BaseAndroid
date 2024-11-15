package com.example.appdemo.ui.apiPaging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appdemo.network.Result
import com.example.appdemo.network.repository.GetPokemonPagingService
import com.example.appdemo.network.responseClass.Pokemon
import kotlinx.coroutines.launch

class PokemonDataPagingViewModel(
    private val getPokemonPagingService: GetPokemonPagingService
):ViewModel() {

    private val _pokemonDataPaging = MutableLiveData<Result<List<Pokemon>>>()
    val pokemonDataPaging: LiveData<Result<List<Pokemon>>> get() = _pokemonDataPaging

    fun fetchPokemonDataPaging(limit:Int,offset:Int) {
        viewModelScope.launch {

            _pokemonDataPaging.value = Result.Loading

            val result = getPokemonPagingService.getPokemonPagingData(limit,offset)
            _pokemonDataPaging.value = result
        }
    }
}