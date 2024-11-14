package com.example.appdemo.network.responseClass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Parcelize
@Serializable
data class Pokemon(
    @SerialName("name")
    val name: String? = "",
    @SerialName("url")
    val url: String? = "",
):Parcelable
