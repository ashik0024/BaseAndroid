package com.example.appdemo.Network.Retrofit

import com.example.appdemo.Network.ReponseClass.PokemonListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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