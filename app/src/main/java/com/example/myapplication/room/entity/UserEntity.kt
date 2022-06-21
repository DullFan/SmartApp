package com.example.myapplication.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "uid") var uid:Int = 0,
    @ColumnInfo(name = "username") var username:String = "",
    @ColumnInfo(name = "password") var password:String = "",
    @ColumnInfo(name = "trueName") var trueName:String = "",
    @ColumnInfo(name = "sex") var sex:String = "0",
    @ColumnInfo(name = "telephone") var telephone:String = "",
    @ColumnInfo(name = "birth") var birth:String= "",
    @ColumnInfo(name = "balance") var balance:Double= 0.toDouble(),
    @ColumnInfo(name = "regtime") var regtime:String= "",
    @ColumnInfo(name = "role") var role:Int = 0,
){
    @Ignore
    constructor():this(1)
}