package com.example.appdemo.roomDb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.appdemo.network.responseClass.Pokemon

@Dao
interface UserInfoDao {

    @Upsert
    suspend fun addOrUpdateUserInfo(userInfo: UserInfo)

    @Delete
    suspend fun deleteUserInfo(userInfo: UserInfo)

    @Query("SELECT * FROM userinfo ORDER BY id ASC")
    suspend fun getAllUsersInfo():List<UserInfo>



    @Upsert
    suspend fun addOrUpdatePlayerInfo(userInfo: PlayerInfo)

    @Query("SELECT * FROM playerInfo ORDER BY playerId ASC")
    suspend fun getAllPlayerInfo():List<PlayerInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemonList: List<Pokemon>)

    @Query("SELECT * FROM pokemon_table ORDER BY id ASC")
    suspend fun getAllPokemonInfo():List<Pokemon>

}