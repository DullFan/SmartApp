package com.example.myapplication.api

import com.example.myapplication.bean.*
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApi {

    /**
     * 电竞资讯
     */
    @GET("/esports/index")
    suspend fun newType01Request(
        @Query("key") key:String = "56d87f8f93b20217e6c25bcf330760da",
        @Query("num") num:Int = 30,
    ):TianXingDataBean

    /**
     * 垃圾分类
     */
    @GET("/lajifenleinews/index")
    suspend fun newType02Request(
        @Query("key") key:String = "56d87f8f93b20217e6c25bcf330760da",
        @Query("num") num:Int = 30,
    ):TianXingDataBean

    /**
     * 宠物新闻
     */
    @GET("/petnews/index")
    suspend fun newType03Request(
        @Query("key") key:String = "56d87f8f93b20217e6c25bcf330760da",
        @Query("num") num:Int = 30,
    ):TianXingDataBean

    /**
     * 天行机器人
     */
    @GET("/robot/index")
    suspend fun robotRequest(
        @Query("key") key:String = "aaad6a601b6d6e22d4593e57f023f85d",
        @Query("question") question:String,
    ):RobotBean


    /**
     * 天气质量
     */
    @GET("/aqi/index")
    suspend fun airQualityRequest(
        @Query("key") key:String = "56d87f8f93b20217e6c25bcf330760da",
        @Query("area") area:String,
    ): AirQualityBean

    /**
     * 七日天气
     */
    @GET("/tianqi/index")
    suspend fun weatherRequest(
    @Query("key") key:String = "56d87f8f93b20217e6c25bcf330760da",
    @Query("city") city:String,
    ): WeatherBean
}