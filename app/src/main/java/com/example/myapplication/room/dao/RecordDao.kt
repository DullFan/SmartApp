package com.example.myapplication.room.dao

import androidx.room.*
import com.example.myapplication.room.entity.RecordEntity
import com.example.myapplication.room.entity.UserEntity
import com.example.myapplication.utils.KV
import com.example.myapplication.utils.MMKVData

@Dao
interface RecordDao {

    @Insert()
    fun insert(vararg recordEntity: RecordEntity)

    @Delete()
    fun delete(recordEntity: RecordEntity)

    @Update()
    fun update(recordEntity: RecordEntity)

    /**
     * 根据用户名查询订单
     */
    @Query("select * from record where opUser = :name")
    fun getAll(name:String = KV.decodeString(MMKVData.USER_NAME)):List<RecordEntity>
}