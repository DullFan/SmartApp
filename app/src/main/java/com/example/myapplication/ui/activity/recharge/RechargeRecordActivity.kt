package com.example.myapplication.ui.activity.recharge

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityRechargeRecordBinding
import com.example.myapplication.databinding.ItemRechargeRecordLayoutBinding
import com.example.myapplication.room.database.AppDataBase
import com.example.myapplication.room.entity.RecordEntity
import com.example.wonder.base.BaseRecyclerViewAdapter
import kotlin.collections.reversed

class RechargeRecordActivity : BaseActivity() {
    val viewDataBinding by lazy {
        ActivityRechargeRecordBinding.inflate(layoutInflater)
    }

    val recordDao by lazy {
        AppDataBase.getAppDataBase(this).recordDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDataBinding.root)

        val listBean: List<RecordEntity> = recordDao.getAll()

        if (listBean.isNotEmpty()) {
            viewDataBinding.text.visibility = View.GONE
            viewDataBinding.rv.visibility = View.VISIBLE
            viewDataBinding.rv.apply {
                layoutManager = LinearLayoutManager(this@RechargeRecordActivity)
                adapter = object : BaseRecyclerViewAdapter<RecordEntity>(
                    R.layout.item_recharge_record_layout,
                    recordDao.getAll().reversed()
                ) {
                    override fun onBind(
                        rvDataBinding: ViewDataBinding,
                        data: RecordEntity,
                        position: Int
                    ) {
                        rvDataBinding as ItemRechargeRecordLayoutBinding
                        rvDataBinding.itemText1.text = "充值${data.money}元"
                        rvDataBinding.itemText2.text = data.opTime
                    }
                }
            }
        } else {
            viewDataBinding.text.visibility = View.VISIBLE
            viewDataBinding.rv.visibility = View.GONE
        }
    }
}