package com.example.appdemo.ui.apiPaging

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.appdemo.common.BaseListRepositoryImpl
import com.example.appdemo.common.BaseNetworkPagingSource
import com.example.appdemo.network.Result
import com.example.appdemo.network.repository.GetPokemonPagingService
import com.example.appdemo.network.responseClass.Pokemon
import com.example.appdemo.roomDb.UserInfoDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class PokemonDataPagingViewModel  @Inject constructor(
    private val getPokemonPagingService: GetPokemonPagingService,
    private val dao: UserInfoDao
) : ViewModel() {

    fun loadPokemonData(limit: Int, offset: Int): Flow<PagingData<Pokemon>> {
        return BaseListRepositoryImpl({
            BaseNetworkPagingSource(
                getPokemonPagingService,
                limit,
                offset
            )
        }).getList(limit).cachedIn(viewModelScope)
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