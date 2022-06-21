package com.example.myapplication.ui.activity.my

import android.content.Intent
import android.os.Bundle
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityChangePasswordBinding
import com.example.myapplication.room.database.AppDataBase
import com.example.myapplication.ui.activity.login.LoginActivity
import com.example.myapplication.utils.KV
import com.example.myapplication.utils.MMKVData
import com.example.myapplication.utils.showLog
import com.example.myapplication.utils.showToast
import com.xw.repo.security.EncryptHelper


class ChangePasswordActivity : BaseActivity() {
    val viewDataBinding by lazy {
        ActivityChangePasswordBinding.inflate(layoutInflater)
    }
    val userDao by lazy {
        AppDataBase.getAppDataBase(this).userDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDataBinding.root)
        viewDataBinding.changeButton.setOnClickListener {
            val et1 = viewDataBinding.changeEt1.text.toString()
            val et2 = viewDataBinding.changeEt2.text.toString()
            val et3 = viewDataBinding.changeEt3.text.toString()
            if (et1.isBlank() && et2.isBlank() && et3.isBlank()) {
                "请输入内容".showToast(this)
                return@setOnClickListener
            }

            val password =
                userDao.findByNameReturnUserEntity(KV.decodeString(MMKVData.USER_NAME)).password
            val encryptHelper = EncryptHelper(this)
            //解密
            val decrypt = encryptHelper.decrypt(password)

            if (decrypt != et1) {
                decrypt.showLog()
                et1.showLog()
                "旧密码不正确".showToast(this)
                return@setOnClickListener
            }

            if (et2 != et3) {
                "两次密码不一致".showToast(this)
                return@setOnClickListener
            }

            //加密
            val encrypt = encryptHelper.encrypt(et2)

            val updatePassword =
                userDao.updatePassword(KV.decodeString(MMKVData.USER_NAME), encrypt)

            if (updatePassword != 0) {
                "修改成功".showToast(this)
                val intent = Intent(this,LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } else {
                "修改失败".showToast(this)
            }
        }


    }
}