package com.example.myapplication.room.dao

import androidx.room.*
import com.example.myapplication.room.entity.UserEntity

@Dao
interface UserDao {

    @Insert
    fun insert(userEntity: UserEntity)

    @Delete
    fun delete(userEntity: UserEntity)

    @Update
    fun update(userEntity: UserEntity)

    /**
     * 根据Id查询用户
     */
    @Query("select * from user where uid = :id")
    fun findByuId(id:Int):UserEntity

    /**
     * 查询所有用户
     */
    @Query("select * from user")
    fun getAll():List<UserEntity>

    /**
     * 根据用户名查找是否存在
     */
    @Query("select * from user where username = :name")
    fun findByName(name:String):Boolean

    /**
     * 根据用户名查找用户信息
     */
    @Query("select * from user where username = :name")
    fun findByNameReturnUserEntity(name:String):UserEntity

    /**
     * 根据用户名修改密码
     */
    @Query("update user set password = :password where username = :name")
    fun updatePassword(name:String,password:String):Int

    /**
     * 根据用户名修改余额
     */
    @Query("update user set balance = :money where username = :name")
    fun updateMoney(name:String,money:Double):Int
}