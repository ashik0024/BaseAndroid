package com.example.appdemo.Network.Repository

import com.example.appdemo.Network.ReponseClass.PokemonListResponse
import com.example.appdemo.Network.Retrofit.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.appdemo.Network.Result
import okhttp3.ResponseBody.Companion.toResponseBody

import java.io.IOException
import retrofit2.HttpException
import retrofit2.Response

class GetPokemonService {

    // Function to fetch Pokemon data from the network
    suspend fun getPokemonData(): Result<PokemonListResponse> {
        return withContext(Dispatchers.IO) {
            try {

                val response = RetrofitClient.apiService.getPokemon()
                Result.Success(response)
            } catch (e: IOException) {

                // Handle network errors (e.g., no internet connection)
                Result.Error(IOException("Network error, please check your connection", e))
            } catch (e: HttpException) {

                // Handle HTTP errors (e.g., 404, 500 errors)
                val errorMessage = e.response()?.errorBody()?.string()
                    ?: "An unexpected HTTP error occurred"
                Result.Error(HttpException(e.response() ?: Response.error<Any>(500, "".toResponseBody())))
            } catch (e: Exception) {

                // Handle any other unexpected errors
                Result.Error(Exception("An unknown error occurred", e))
            }
        }
    }
}

//class PackPaymentMethodService @Inject constructor (
//    private val toffeeApi: ToffeeApi,
//    private val preference: SessionPreference
//) {
//
//    suspend fun loadData(packId: Int) : PackPaymentMethodBean? {
//
//        val request =  PackPaymentMethodRequest(
//            preference.customerId,
//            preference.password
//        )
//        val isBlNumber = if ( preference.isBanglalinkNumber=="true") 1 else 0
//
//        val response = tryIO {
//            toffeeApi.getPackPaymentMethods(
//                isBlNumber,
//                packId,
//                preference.getDBVersionByApiName(ApiNames.PACKAGE_WISE_PREMIUM_PACK),
//                request
//            )
//        }
//
//        return response.response
//    }
//}