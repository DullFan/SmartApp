package com.example.myapplication.ui.activity.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityUserDetailsBinding
import com.example.myapplication.room.database.AppDataBase
import com.example.myapplication.utils.showToast

class UserDetailsActivity : BaseActivity() {
    val viewDataBinding by lazy {
        ActivityUserDetailsBinding.inflate(layoutInflater)
    }

    val userDao by lazy {
        AppDataBase.getAppDataBase(this).userDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDataBinding.root)
        val stringExtra = intent.getStringExtra("user")
        val findByNameReturnUserEntity = userDao.findByNameReturnUserEntity(stringExtra!!)
        viewDataBinding.personalInformationName.text = findByNameReturnUserEntity.username

        if (findByNameReturnUserEntity.trueName != "") {
            viewDataBinding.personalInformationEt1.setText(findByNameReturnUserEntity.trueName)
            viewDataBinding.personalInformationEt1.setTextColor(resources.getColor(R.color.black))
        } else {
            viewDataBinding.personalInformationEt1.text = "未填"
            viewDataBinding.personalInformationEt1.setTextColor(resources.getColor(R.color.gray))
        }

        if (findByNameReturnUserEntity.sex == "0") {
            viewDataBinding.personalInformationSex.text = "男"
        } else {
            viewDataBinding.personalInformationSex.text = "女"
        }

        if (findByNameReturnUserEntity.telephone != "") {
            viewDataBinding.personalInformationEt2.setText(findByNameReturnUserEntity.telephone)
            viewDataBinding.personalInformationEt2.setTextColor(resources.getColor(R.color.black))
        } else {
            viewDataBinding.personalInformationEt2.text = "未填"
            viewDataBinding.personalInformationEt2.setTextColor(resources.getColor(R.color.gray))
        }

        if (findByNameReturnUserEntity.birth != "") {
            viewDataBinding.personalInformationChusheng.setText(findByNameReturnUserEntity.birth)
            viewDataBinding.personalInformationChusheng.setTextColor(resources.getColor(R.color.black))
        } else {
            viewDataBinding.personalInformationChusheng.setTextColor(resources.getColor(R.color.gray))
            viewDataBinding.personalInformationChusheng.text = "未填"
        }

        viewDataBinding.personalInformationTime.text = findByNameReturnUserEntity.regtime

        viewDataBinding.personalInformationMoney.text = "${findByNameReturnUserEntity.balance}"
    }
}