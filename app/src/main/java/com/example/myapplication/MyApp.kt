package com.example.myapplication

import android.app.Application
import com.lljjcoder.style.citylist.utils.CityListLoader
import com.tencent.mmkv.MMKV

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        CityListLoader.getInstance().loadProData(this);
        MMKV.initialize(this)
    }
}