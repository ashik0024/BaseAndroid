package com.example.appdemo.network.repository

import com.example.appdemo.network.retrofit.RetrofitClient
import com.example.appdemo.network.Result
import com.example.appdemo.network.responseClass.Pokemon


class GetPokemonService {

    suspend fun getPokemonData(): Result<List<Pokemon>> {
        return safeApiCall {
            val response = RetrofitClient.apiService.getPokemon()
            response.results ?: throw Exception("Empty response, no Pokemon data available")
        }
    }
}

