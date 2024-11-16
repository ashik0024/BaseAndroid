package com.example.appdemo.network.responseClass

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Entity(tableName = "pokemon_table")
@Parcelize
@Serializable
data class Pokemon(
   @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @SerialName("name")
    val name: String? = "",
    @SerialName("url")
    val url: String? = "",
):Parcelable
