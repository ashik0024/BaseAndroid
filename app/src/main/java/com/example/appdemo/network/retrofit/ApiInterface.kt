package com.example.appdemo.network.retrofit

import com.example.appdemo.network.responseClass.PokemonInfo
import com.example.appdemo.network.responseClass.PokemonListResponse

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("pokemon")
    suspend fun getPokemon(): PokemonListResponse

    @GET("pokemon")
    suspend fun getPokemonPaging(
    @Query("limit") limit: Int,
    @Query("offset") offset: Int
    ): PokemonListResponse


    @GET("pokemon/{pokemonNameOrId}")
    suspend fun getPokemonDetails(
        @Path("pokemonNameOrId") pokemonNameOrId:String,
    ): PokemonInfo

}