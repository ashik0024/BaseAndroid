package com.example.appdemo.roomDb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class UserInfo (

    @PrimaryKey(autoGenerate = true)
    var id:Int?=null,

    var name: String?="",
    var phoneNum: Int?=0,
    var country: String?="",
    @ColumnInfo(name = "createdTime", defaultValue = "")
    var createdTime:String= System.currentTimeMillis().toString()

//    @ColumnInfo(name = "createdTime", defaultValue = "")
//var createdTime:String= System.currentTimeMillis().toString()
)