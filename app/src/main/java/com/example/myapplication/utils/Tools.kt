package com.example.myapplication.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.myapplication.bean.WeatherBean
import com.lljjcoder.style.citypickerview.CityPickerView
import com.tencent.mmkv.MMKV
import java.text.SimpleDateFormat
import java.util.*

private var toast: Toast? = null
val WEB_VIEW_URL = "WEB_VIEW_URL"

lateinit var weatherBeanData: WeatherBean
/**
 * 封装Toast
 */
fun Any.showToast(context: Context?) {
    if (toast == null) {
        toast = Toast.makeText(context, this.toString(), Toast.LENGTH_SHORT)
    } else {
        toast!!.cancel()
        toast = Toast.makeText(context, this.toString(), Toast.LENGTH_SHORT)
    }
    toast!!.show()
}

/**
 * 打Log
 */
fun Any.showLog() {
    Log.e("showLog", "$this")
}

/**
 * 将时间戳转换成 年/月/日
 */
fun formatTime(length: Long = System.currentTimeMillis()): String {
    val date = Date(length)
    //时间格式化工具
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return sdf.format(date)
}