package com.example.appdemo.network.retrofit

import com.example.appdemo.network.responseClass.PokemonListResponse
import retrofit2.http.GET

interface ApiInterface {

    @GET("pokemon")
    suspend fun getPokemon(): PokemonListResponse



//    @POST("/package-wise-data-pack/{isBlNumber}/{packageId}/{dbVersion}")
//    suspend fun getPackPaymentMethods(
//        @Path("isBlNumber") isBlNumber: Int,
//        @Path("packageId") packId: Int,
//        @Path("dbVersion") dbVersion: Int,
//        @Body packPaymentMethodRequest: PackPaymentMethodRequest
//    ): PackPaymentMethodResponse
}