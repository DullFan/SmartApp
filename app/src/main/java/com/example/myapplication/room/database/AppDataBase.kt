package com.example.myapplication.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.room.dao.RecordDao
import com.example.myapplication.room.dao.UserDao
import com.example.myapplication.room.entity.RecordEntity
import com.example.myapplication.room.entity.UserEntity

@Database(entities = [UserEntity::class, RecordEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun userDao():UserDao
    abstract fun recordDao():RecordDao


    companion object {
        private var instance: AppDataBase? = null

        @Synchronized
        fun getAppDataBase(context: Context): AppDataBase {
            return instance ?: Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                "dull_fan"
            ).allowMainThreadQueries().build().apply { instance = this }
        }
    }
}