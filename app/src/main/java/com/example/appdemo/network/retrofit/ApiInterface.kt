package com.example.appdemo.network.retrofit

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


//    @POST("/package-wise-data-pack/{isBlNumber}/{packageId}/{dbVersion}")
//    suspend fun getPackPaymentMethods(
//        @Path("isBlNumber") isBlNumber: Int,
//        @Path("packageId") packId: Int,
//        @Path("dbVersion") dbVersion: Int,
//        @Body packPaymentMethodRequest: PackPaymentMethodRequest
//    ): PackPaymentMethodResponse
}