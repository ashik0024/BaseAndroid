package com.example.appdemo.ui.apiPaging

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appdemo.network.Result
import com.example.appdemo.network.repository.GetPokemonPagingService
import com.example.appdemo.network.responseClass.Pokemon
import com.example.appdemo.roomDb.UserInfoDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class PokemonDataPagingViewModel  @Inject constructor(
    private val getPokemonPagingService: GetPokemonPagingService,
    private val dao: UserInfoDao
) : ViewModel() {

    private val _pokemonDataPaging = MutableLiveData<Result<List<Pokemon>>>()
    val pokemonDataPaging: LiveData<Result<List<Pokemon>>> get() = _pokemonDataPaging

    fun fetchPokemonDataPaging(limit:Int,offset:Int) {
        viewModelScope.launch {

            _pokemonDataPaging.value = Result.Loading

            val result = getPokemonPagingService.getPokemonPagingData(limit,offset)
            _pokemonDataPaging.value = result
        }
    }

    fun saveToDatabase(pokemonList: List<Pokemon>) {
        viewModelScope.launch {
            dao.insertAll(pokemonList)
        }
    }
    fun getAllPlayerInfo() {
        viewModelScope.launch {
            Log.d("getAllPlayerInfo", ": "+dao.getAllPokemonInfo())

        }
    }
}