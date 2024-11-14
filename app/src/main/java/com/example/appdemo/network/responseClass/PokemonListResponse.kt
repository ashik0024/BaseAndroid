package com.example.appdemo.network.responseClass
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class PokemonListResponse (
    @SerialName("count")
    val count: Int? = 0,
    @SerialName("next")
    val next: String? = "",
    @SerialName("previous")
    val previous: String? = "",
    @SerialName("results")
    val results : List<Pokemon>? = null,
):Parcelable



//@Serializable
//data class PackPaymentMethodResponse(
//    @SerialName("apiLanguage")
//    val apiLanguage: String? = null,
//    @SerialName("debugCode")
//    val debugCode: Int = 0,
//    @SerialName("debugMsg")
//    val debugMsg: String? = null,
//    @SerialName("response")
//    val response: PackPaymentMethodBean? = null,
//) : BaseResponse()