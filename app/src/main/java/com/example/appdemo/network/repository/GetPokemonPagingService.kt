package com.example.appdemo.network.repository

import com.example.appdemo.common.BaseApiService
import com.example.appdemo.network.Result
import com.example.appdemo.network.responseClass.Pokemon
import com.example.appdemo.network.retrofit.ApiInterface

import javax.inject.Inject



class GetPokemonPagingService @Inject constructor(
    private val apiService: ApiInterface
) : BaseApiService<Pokemon> {

    override suspend fun loadData(offset: Int, limit: Int): List<Pokemon> {

        val response = apiService.getPokemonPaging(limit, offset)
        return  response.results ?: throw Exception("Empty response, no Pokemon data available")
    }

}


