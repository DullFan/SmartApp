package com.example.myapplication.ui.activity.weather

import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.bean.WeatherNewslist
import com.example.myapplication.databinding.ActivityWeatherPolylineBinding
import com.example.myapplication.utils.weatherBeanData
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter


class WeatherPolylineActivity : BaseActivity() {
    val viewDataBinding by lazy {
        ActivityWeatherPolylineBinding
            .inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDataBinding.root)
        val newslist = weatherBeanData.newslist
        initData(newslist)

        viewDataBinding.myLinceChart.apply {

            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setAvoidFirstLastClipping(false)

            setTouchEnabled(true);
            isDragEnabled = true;

            axisRight.isEnabled = false
            axisLeft.isEnabled = false
            legend.isEnabled = false
            xAxis.isEnabled = false
            setScaleEnabled(false)
            setVisibleXRangeMaximum(6f)
            isDoubleTapToZoomEnabled = false
            setDrawGridBackground(false)
            description.isEnabled = false
            val list: ArrayList<Entry> = ArrayList()
            val list2: ArrayList<Entry> = ArrayList()
            for (i in newslist.indices) {
                list.add(
                    Entry(
                        i.toFloat(),
                        newslist[i].lowest.substring(0, newslist[i].lowest.length - 1).toFloat()
                    )
                )
                list2.add(
                    Entry(
                        i.toFloat(),
                        newslist[i].highest.substring(0, newslist[i].highest.length - 1).toFloat()
                    )
                )
            }



            val lineDataSet = LineDataSet(list, "")
            val lineDataSet2 = LineDataSet(list2, "")

            lineDataSet.setCircleColor(resources.getColor(R.color.theme_color))
            lineDataSet.setDrawCircleHole(true)
            lineDataSet2.setDrawCircleHole(true)
            lineDataSet2.setCircleColor(resources.getColor(R.color.theme_color))
            lineDataSet.isHighlightEnabled = false
            lineDataSet2.isHighlightEnabled = false
            lineDataSet.color = resources.getColor(R.color.theme_color)
            lineDataSet2.color = resources.getColor(R.color.theme_color)

            lineDataSet.valueTextSize = 10f
            lineDataSet.valueTextColor = resources.getColor(R.color.theme_color)
            lineDataSet2.valueTextSize = 10f
            lineDataSet2.valueTextColor = resources.getColor(R.color.theme_color)

            val lineData = LineData(lineDataSet, lineDataSet2)
            data = lineData


        }
    }

    private fun initData(newslist: List<WeatherNewslist>) {
        viewDataBinding.tvWeek01.text = "今天"
        viewDataBinding.tvDate01.text = newslist[0].date
        viewDataBinding.tvWeatherDay01.text = newslist[0].weather
        viewDataBinding.tvWeatherNight01.text = newslist[0].weather
        viewDataBinding.tvWind01.text = newslist[0].wind
        viewDataBinding.tvWindLevel01.text = newslist[0].windsc


        viewDataBinding.tvWeek02.text = "明天"
        viewDataBinding.tvDate02.text = newslist[1].date
        viewDataBinding.tvWeatherDay02.text = newslist[1].weather
        viewDataBinding.tvWeatherNight02.text = newslist[1].weather
        viewDataBinding.tvWind02.text = newslist[1].wind
        viewDataBinding.tvWindLevel02.text = newslist[1].windsc



        viewDataBinding.tvWeek03.text = newslist[2].week
        viewDataBinding.tvDate03.text = newslist[2].date
        viewDataBinding.tvWeatherDay03.text = newslist[2].weather
        viewDataBinding.tvWeatherNight03.text = newslist[2].weather
        viewDataBinding.tvWind03.text = newslist[2].wind
        viewDataBinding.tvWindLevel03.text = newslist[2].windsc


        viewDataBinding.tvWeek04.text = newslist[3].week
        viewDataBinding.tvDate04.text = newslist[3].date
        viewDataBinding.tvWeatherDay04.text = newslist[3].weather
        viewDataBinding.tvWeatherNight04.text = newslist[3].weather
        viewDataBinding.tvWind04.text = newslist[3].wind
        viewDataBinding.tvWindLevel04.text = newslist[3].windsc


        viewDataBinding.tvWeek05.text = newslist[4].week
        viewDataBinding.tvDate05.text = newslist[4].date
        viewDataBinding.tvWeatherDay05.text = newslist[4].weather
        viewDataBinding.tvWeatherNight05.text = newslist[4].weather
        viewDataBinding.tvWind05.text = newslist[4].wind
        viewDataBinding.tvWindLevel05.text = newslist[4].windsc


        viewDataBinding.tvWeek06.text = newslist[5].week
        viewDataBinding.tvDate06.text = newslist[5].date
        viewDataBinding.tvWeatherDay06.text = newslist[5].weather
        viewDataBinding.tvWeatherNight06.text = newslist[5].weather
        viewDataBinding.tvWind06.text = newslist[5].wind
        viewDataBinding.tvWindLevel06.text = newslist[5].windsc


        viewDataBinding.tvWeek07.text = newslist[6].week
        viewDataBinding.tvDate07.text = newslist[6].date
        viewDataBinding.tvWeatherDay07.text = newslist[6].weather
        viewDataBinding.tvWeatherNight07.text = newslist[6].weather
        viewDataBinding.tvWind07.text = newslist[6].wind
        viewDataBinding.tvWindLevel07.text = newslist[6].windsc



    }
}