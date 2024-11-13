package com.example.appdemo.Network.Repository

import java.io.IOException
import retrofit2.HttpException
import retrofit2.Response
import com.example.appdemo.Network.Result
import okhttp3.ResponseBody.Companion.toResponseBody

open class BaseRepository {

    protected suspend fun <T> safeApiCall(call: suspend () -> Response<T>): Result<T> {
        return try {


            val response = call()
            // Check if the response is successful (status code 2xx)
            if (response.isSuccessful) {

                // If successful, return the data wrapped in Result.Success
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Error(Exception("Empty response body"))
            } else {

                // If not successful, return the error message
                Result.Error(HttpException(response))
            }
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