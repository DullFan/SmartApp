package com.example.myapplication.ui.activity.robot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.bean.RobotStringBean
import com.example.myapplication.databinding.ActivityRobotBinding
import com.example.myapplication.utils.showLog
import com.example.myapplication.viewmodel.MyViewModel
import com.example.wonder.base.BaseRecyclerViewAdapter
import com.example.wonder.base.LIFT_ROBOT
import com.example.wonder.base.RIGHT_ROBOT
import com.example.wonder.base.RobotAdapter

class RobotActivity : BaseActivity() {
    val viewDataBinding by lazy {
        ActivityRobotBinding.inflate(layoutInflater)
    }
    val stringList = mutableListOf<RobotStringBean>()

    val baseAdapter = RobotAdapter(stringList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDataBinding.root)

        viewDataBinding.send.setOnClickListener {
            val toString = viewDataBinding.message.text.toString()
            if (toString.isBlank()) {
                "请输入文本".showLog()
                return@setOnClickListener
            }
            viewModel.robotRequest(toString)
            stringList.add(RobotStringBean("", toString, RIGHT_ROBOT))
            baseAdapter.dataList = stringList
            viewDataBinding.message.setText("")
        }

        viewModel.robotLiveData.observe(this) {
            stringList.add(RobotStringBean("", it.newslist[0].reply, LIFT_ROBOT))
            baseAdapter.dataList = stringList
        }

        viewDataBinding.rv.apply {
            layoutManager = LinearLayoutManager(this@RobotActivity)
            adapter = baseAdapter
        }

    }
}