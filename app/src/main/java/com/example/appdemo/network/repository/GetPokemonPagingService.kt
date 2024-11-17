package com.example.appdemo.network.repository

import com.example.appdemo.network.Result
import com.example.appdemo.network.responseClass.Pokemon
import com.example.appdemo.network.retrofit.ApiInterface

import javax.inject.Inject


class GetPokemonPagingService @Inject constructor(
    private val apiService: ApiInterface
) {

    suspend fun getPokemonPagingData(limit: Int, offset: Int): Result<List<Pokemon>> {
        return safeApiCall {
            val response = apiService.getPokemonPaging(limit, offset)
            response.results ?: throw Exception("Empty response, no Pokemon data available")
        }
    }
}


