package com.example.appdemo.roomDb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class PlayerInfo(
    @PrimaryKey(autoGenerate = true)
    var playerId: Int = 0,
    var playerName: String? = null // nullable String, default value can be null
)
