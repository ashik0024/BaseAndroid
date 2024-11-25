package com.example.appdemo.ui.apiNonPaging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appdemo.network.Result
import com.example.appdemo.network.repository.GetPokemonPagingService
import com.example.appdemo.network.repository.PokemonDetailsService
import com.example.appdemo.network.responseClass.Pokemon
import com.example.appdemo.network.responseClass.PokemonInfo
import com.example.appdemo.network.responseClass.pokemonItem.PokemonDetails
import com.example.appdemo.roomDb.UserInfoDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val pokemonDetailsService: PokemonDetailsService,
) : ViewModel() {


    private val _pokemonDetailsData = MutableLiveData<Result<PokemonInfo>>()
    val pokemonDetailsData: LiveData<Result<PokemonInfo>> get() = _pokemonDetailsData

    fun fetchPokemonData(pokemonNameOrId:String) {
        viewModelScope.launch {

            _pokemonDetailsData.value = Result.Loading

            val result = pokemonDetailsService.getPokemonDetails(pokemonNameOrId)
            _pokemonDetailsData.value = result
        }
    }
}