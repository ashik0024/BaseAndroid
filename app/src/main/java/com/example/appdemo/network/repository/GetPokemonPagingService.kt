package com.example.appdemo.network.repository

import com.example.appdemo.common.BaseApiService
import com.example.appdemo.network.responseClass.Pokemon
import com.example.appdemo.network.retrofit.ApiInterface

import javax.inject.Inject



class GetPokemonPagingService @Inject constructor(
    private val apiService: ApiInterface
) : PagingErrorHandling(), BaseApiService<Pokemon> {

    override suspend fun loadData(offset: Int, limit: Int): List<Pokemon> {
        return safeApiCallPaging {
            val response = apiService.getPokemonPaging(limit, offset)

            if (response.results.isNullOrEmpty()) {
                throw Exception("No Pokemon data available")
            }

            response.results
        }
    }
}


