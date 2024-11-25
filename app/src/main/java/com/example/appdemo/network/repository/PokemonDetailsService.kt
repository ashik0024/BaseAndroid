package com.example.appdemo.network.repository

import com.example.appdemo.network.retrofit.RetrofitClient
import com.example.appdemo.network.Result
import com.example.appdemo.network.responseClass.Pokemon
import com.example.appdemo.network.responseClass.PokemonInfo
import com.example.appdemo.network.responseClass.pokemonItem.PokemonDetails
import com.example.appdemo.network.retrofit.ApiInterface
import javax.inject.Inject


class PokemonDetailsService @Inject constructor(
    private val apiService: ApiInterface) {

    suspend fun getPokemonDetails(pokemonNameOrId:String): Result<PokemonInfo> {
        return safeApiCall {
            val response = apiService.getPokemonDetails(pokemonNameOrId)
            response ?: throw Exception("Empty response, no Pokemon data available")
        }
    }
}

