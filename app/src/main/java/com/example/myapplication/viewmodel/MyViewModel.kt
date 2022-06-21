package com.example.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.bean.AirQualityBean
import com.example.myapplication.bean.RobotBean
import com.example.myapplication.bean.TianXingDataBean
import com.example.myapplication.bean.WeatherBean
import com.example.myapplication.repository.Repository
import com.example.wonder.entity.APiClient
import kotlinx.coroutines.launch


class MyViewModel : ViewModel() {
    var welcomeTextNumber = MutableLiveData<String>("跳过 3")


    var newType01LiveData = MutableLiveData<TianXingDataBean>()
    var newType02LiveData = MutableLiveData<TianXingDataBean>()
    var newType03LiveData = MutableLiveData<TianXingDataBean>()
    var robotLiveData = MutableLiveData<RobotBean>()
    var airQualityLiveData = MutableLiveData<AirQualityBean>()
    var weatherLiveData = MutableLiveData<WeatherBean>()

    fun newType01Request() {
        viewModelScope.launch {
            newType01LiveData.value = Repository().newType01Request()
        }
    }

    fun newType02Request() {
        viewModelScope.launch {
            newType02LiveData.value = Repository().newType02Request()
        }
    }

    fun newType03Request() {
        viewModelScope.launch {
            newType03LiveData.value = Repository().newType03Request()
        }
    }

    fun robotRequest(data:String) {
        viewModelScope.launch {
            robotLiveData.value = Repository().robotRequest(data)
        }
    }

    fun airQualityRequest(area:String) {
        viewModelScope.launch {
            airQualityLiveData.value = Repository().airQualityRequest(area)
        }
    }

    fun weatherRequest(city:String) {
        viewModelScope.launch {
            weatherLiveData.value = Repository().weatherRequest(city)
        }
    }


}