package com.example.myapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.bean.WeatherBean
import com.example.myapplication.databinding.FragmentWeatherBinding
import com.example.myapplication.ui.activity.weather.WeatherPolylineActivity
import com.example.myapplication.utils.KV
import com.example.myapplication.utils.MMKVData
import com.example.myapplication.utils.showLog
import com.example.myapplication.utils.weatherBeanData
import com.lljjcoder.Interface.OnCityItemClickListener
import com.lljjcoder.bean.CityBean
import com.lljjcoder.bean.DistrictBean
import com.lljjcoder.bean.ProvinceBean
import com.lljjcoder.citywheel.CityConfig
import com.lljjcoder.style.citylist.Toast.ToastUtils
import com.lljjcoder.style.citypickerview.CityPickerView


class WeatherFragment : BaseFragment() {

    val viewDataBinding by lazy {
        FragmentWeatherBinding.inflate(layoutInflater)
    }

    var string1: String = "福建省"
    var string2: String = "福州市"
    var string3: String = "仓山区"

    //申明对象
    var mPicker = CityPickerView()

    lateinit var weatherBean:WeatherBean

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mPicker.init(requireContext())
        initLayout1()
        initCity("福州市","福州市")
        viewModel.airQualityLiveData.observe(viewLifecycleOwner) {
            viewDataBinding.kongqi.text =
                "PM2.5: ${it.newslist[0].pm2_5}μg/m³、空气质量: ${it.newslist[0].quality}"
        }
        viewModel.weatherLiveData.observe(viewLifecycleOwner) {
            weatherBean = it
            viewDataBinding.weatherTianqi.text = it.newslist[0].weather
            viewDataBinding.weatherDu.text = it.newslist[0].real

            viewDataBinding.tianqi1.text = "今天 · ${it.newslist[0].weather}"
            viewDataBinding.tianqi2.text = "明天 · ${it.newslist[1].weather}"
            viewDataBinding.tianqi3.text = "${it.newslist[2].week} · ${it.newslist[2].weather}"

            viewDataBinding.wendu1.text = "${it.newslist[0].lowest} / ${it.newslist[0].highest}"
            viewDataBinding.wendu2.text = "${it.newslist[1].lowest} / ${it.newslist[1].highest}"
            viewDataBinding.wendu3.text = "${it.newslist[2].lowest} / ${it.newslist[2].highest}"
        }

        viewDataBinding.weatherCity.setOnClickListener {
            val cityConfig = CityConfig.Builder()
                .confirTextColor("#ff6750a4")
                .province(string1)
                .city(string2)
                .district(string3)
                .build()

            mPicker.setConfig(cityConfig)
            //监听选择点击事件及返回结果
            mPicker.setOnCityItemClickListener(object : OnCityItemClickListener() {
                override fun onSelected(
                    province: ProvinceBean,
                    city: CityBean,
                    district: DistrictBean
                ) {
                    viewDataBinding.weatherCity.text = "${province.name} ${city.name} ${district.name}"
                    string1 = province.name
                    string2 = city.name
                    string3 =  district.name
                    initCity(district.name,district.name)
                }

                override fun onCancel() {
                    ToastUtils.showLongToast(requireContext(), "已取消")
                }
            })
            mPicker.showCityPicker()
        }

        viewDataBinding.button.setOnClickListener {
            weatherBeanData = weatherBean
            myStartActivity(WeatherPolylineActivity::class.java,"7天趋势预报")
        }
        return viewDataBinding.root
    }

    private fun initCity(s1: String,s2: String) {
        viewModel.airQualityRequest(s1)
        viewModel.weatherRequest(s2)
    }

    private fun initLayout1() {
        if (KV.decodeBool(MMKVData.FLAG_02, true)) {
            viewDataBinding.layout1.visibility = View.VISIBLE
        } else {
            viewDataBinding.layout1.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        initLayout1()
    }
}