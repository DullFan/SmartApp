package com.example.myapplication.ui.activity.my

import android.app.DatePickerDialog
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityPersonalInformationBinding
import com.example.myapplication.room.database.AppDataBase
import com.example.myapplication.room.entity.UserEntity
import com.example.myapplication.utils.KV
import com.example.myapplication.utils.MMKVData
import com.example.myapplication.utils.showLog
import com.example.myapplication.utils.showToast
import java.text.SimpleDateFormat
import java.util.*


class PersonalInformationActivity : BaseActivity() {
    private val viewDataBinding by lazy {
        ActivityPersonalInformationBinding.inflate(layoutInflater)
    }
    val userDao by lazy {
        AppDataBase.getAppDataBase(this).userDao()
    }

    val findByNameReturnUserEntity by lazy {
        userDao.findByNameReturnUserEntity(KV.decodeString(MMKVData.USER_NAME))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDataBinding.root)
        initData()

        initButton()
    }

    private fun initButton() {
        viewDataBinding.personalInformationButton.setOnClickListener {
            val et1 = viewDataBinding.personalInformationEt1.text.toString()
            val et2 = viewDataBinding.personalInformationEt2.text.toString()
            if (et1.isBlank() && et2.isBlank()) {
                "请完善信息".showToast(this)
                return@setOnClickListener
            }

            if (et2.length != 11) {
                "请输入合法的手机号".showToast(this)
                return@setOnClickListener
            }

            if (viewDataBinding.personalInformationChusheng.text.toString() == "未填") {
                "请选择出生日期".showToast(this)
                return@setOnClickListener
            }

            val userEntity = UserEntity(
                findByNameReturnUserEntity.uid,
                findByNameReturnUserEntity.username,
                findByNameReturnUserEntity.password,
                et1,
                if (viewDataBinding.personalInformationNan.isChecked) "0" else "1",
                et2,
                viewDataBinding.personalInformationChusheng.text.toString(),
                findByNameReturnUserEntity.balance,
                findByNameReturnUserEntity.regtime,
                findByNameReturnUserEntity.role,
            )
            userDao.update(userEntity)
            "修改成功".showLog()
            onBackPressed()
        }

        val c: Calendar = Calendar.getInstance(Locale.CHINA)
        val datePickerDialog = DatePickerDialog(
            this,
            { view, year, monthOfYear, dayOfMonth ->
                viewDataBinding.personalInformationChusheng.text =
                    "${year}-${monthOfYear}-${dayOfMonth}"
                viewDataBinding.personalInformationChusheng.setTextColor(resources.getColor(R.color.black))
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        //日期
        viewDataBinding.personalInformationChusheng.setOnClickListener {
            datePickerDialog.show()
        }
    }

    private fun initData() {
        viewDataBinding.personalInformationName.text = findByNameReturnUserEntity.username

        if (findByNameReturnUserEntity.trueName != "") {
            viewDataBinding.personalInformationEt1.setText(findByNameReturnUserEntity.trueName)
        } else {
            viewDataBinding.personalInformationEt1.hint = "未填"
        }

        if (findByNameReturnUserEntity.sex == "0") {
            viewDataBinding.personalInformationNan.isChecked = true
        } else {
            viewDataBinding.personalInformationNv.isChecked = true
        }

        if (findByNameReturnUserEntity.telephone != "") {
            viewDataBinding.personalInformationEt2.setText(findByNameReturnUserEntity.telephone)
        } else {
            viewDataBinding.personalInformationEt2.hint = "未填"
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