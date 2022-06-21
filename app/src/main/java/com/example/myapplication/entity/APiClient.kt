package com.example.wonder.entity

import android.util.Log
import com.example.myapplication.api.MyApi
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.*

import okhttp3.Interceptor
import java.lang.String


val TianXingApi = "http://api.tianapi.com/"

class APiClient {

    companion object {
        val instanceTianXing = APiClient()
    }

    fun instanceRetrofitTianXing():MyApi  {

        val mOkHttpClient = OkHttpClient().newBuilder().apply {
            //添加读取超时时间
            readTimeout(10000, TimeUnit.SECONDS)
            //添加连接超时时间
            connectTimeout(10000, TimeUnit.SECONDS)
            //添加写出超时时间
            writeTimeout(10000, TimeUnit.SECONDS)
        }.build()
        val retrofit = Retrofit.Builder().baseUrl(TianXingApi).client(mOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(MyApi::class.java)
    }
}

