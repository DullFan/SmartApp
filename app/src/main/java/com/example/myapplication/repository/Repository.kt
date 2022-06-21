package com.example.myapplication.repository

import com.example.myapplication.bean.AirQualityBean
import com.example.myapplication.bean.RobotBean
import com.example.myapplication.bean.TianXingDataBean
import com.example.myapplication.bean.WeatherBean
import com.example.wonder.entity.APiClient

class Repository {

    suspend fun newType01Request(): TianXingDataBean {
        return APiClient.instanceTianXing.instanceRetrofitTianXing().newType01Request()
    }

    suspend fun newType02Request(): TianXingDataBean {
        return APiClient.instanceTianXing.instanceRetrofitTianXing().newType02Request()
    }

    suspend fun newType03Request(): TianXingDataBean {
        return APiClient.instanceTianXing.instanceRetrofitTianXing().newType03Request()
    }

    suspend fun robotRequest(question:String): RobotBean {
        return APiClient.instanceTianXing.instanceRetrofitTianXing().robotRequest(question = question)
    }

    suspend fun airQualityRequest(area:String): AirQualityBean {
        return APiClient.instanceTianXing.instanceRetrofitTianXing().airQualityRequest(area = area)
    }

    suspend fun weatherRequest(city:String): WeatherBean {
        return APiClient.instanceTianXing.instanceRetrofitTianXing().weatherRequest(city = city)
    }

}