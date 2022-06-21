package com.example.myapplication.room.entity

import androidx.room.*

@Entity(tableName = "record")
data class RecordEntity (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id:Int = 0,
    @ColumnInfo(name = "money") var money:Double = 0.0,
    @ColumnInfo(name = "opUser") var opUser:String = "",
    @ColumnInfo(name = "opTime") var opTime:String = "",
){
    @Ignore
    constructor():this(1)
}
