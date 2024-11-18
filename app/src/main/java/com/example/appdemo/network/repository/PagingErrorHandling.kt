package com.example.appdemo.network.repository

import okio.IOException
import retrofit2.HttpException

open class PagingErrorHandling {

    /**
     * Safely executes an API call and handles exceptions.
     */
    protected suspend fun <T> safeApiCallPaging(apiCall: suspend () -> T): T {
        return try {
            apiCall.invoke()
        } catch (e: Exception) {
            handleException(e)
        }
    }

    /**
     * Handles exceptions and throws a user-friendly message.
     * This method should not return anything, as it throws exceptions.
     */
    private fun handleException(e: Exception): Nothing {
        throw when (e) {
            is IOException -> Exception("Network error: Please check your internet connection")
            is HttpException -> when (e.code()) {
                404 -> Exception("Data not found (404)")
                500 -> Exception("Server error (500)")
                else -> Exception("Unknown error: ${e.localizedMessage}")
            }
            else -> Exception("Unexpected error: ${e.localizedMessage}")
        }
    }
}


