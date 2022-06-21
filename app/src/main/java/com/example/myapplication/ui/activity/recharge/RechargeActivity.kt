package com.example.myapplication.ui.activity.recharge

import android.app.AlertDialog
import android.os.Bundle
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityRechargeBinding
import com.example.myapplication.room.database.AppDataBase
import com.example.myapplication.room.entity.RecordEntity
import com.example.myapplication.ui.activity.login.LoginActivity
import com.example.myapplication.utils.KV
import com.example.myapplication.utils.MMKVData
import com.example.myapplication.utils.formatTime
import com.example.myapplication.utils.showToast

class RechargeActivity : BaseActivity() {
    val viewDataBinding by lazy {
        ActivityRechargeBinding.inflate(layoutInflater)
    }

    val recordDao by lazy {
        AppDataBase.getAppDataBase(this).recordDao()
    }

    val userDao by lazy {
        AppDataBase.getAppDataBase(this).userDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDataBinding.root)
        initMoney()
        initButton()

        viewDataBinding.rechargeList.setOnClickListener {
            myStartActivity(RechargeRecordActivity::class.java,"充值记录")
        }
    }

    private fun initButton() {
        viewDataBinding.rechargeButton1.setOnClickListener {
            initDialog(50.0)
        }

        viewDataBinding.rechargeButton2.setOnClickListener {
            initDialog(100.0)
        }

        viewDataBinding.rechargeButton3.setOnClickListener {
            initDialog(200.0)
        }


        viewDataBinding.rechargeButton4.setOnClickListener {
            initDialog(300.0)
        }

        viewDataBinding.rechargeButton5.setOnClickListener {
            initDialog(500.0)
        }

    }

    private fun initDialog(money:Double) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("提示")
        builder.setMessage("确定要充值${money}元吗?")
        builder.setPositiveButton(
            "确定"
        ) { dialog, which ->
            addMoney(money)
            initMoney()
        }
        builder.setNegativeButton(
            "取消"
        ) { dialog, which ->

        }
        builder.show()
    }


    fun addMoney(money:Double){
        val balance =
            userDao.findByNameReturnUserEntity(KV.decodeString(MMKVData.USER_NAME)).balance

        userDao.updateMoney(KV.decodeString(MMKVData.USER_NAME),balance + money)

        recordDao.insert(RecordEntity(money = money, opUser = KV.decodeString(MMKVData.USER_NAME), opTime = formatTime()))

        "充值成功".showToast(this)
    }

    private fun initMoney() {
        val balance =
            userDao.findByNameReturnUserEntity(KV.decodeString(MMKVData.USER_NAME)).balance
        viewDataBinding.rechargeText.text = "账户余额: ${balance}元"
    }
}