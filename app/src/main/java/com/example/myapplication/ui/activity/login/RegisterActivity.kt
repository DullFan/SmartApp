package com.example.myapplication.ui.activity.login

import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityRegisterBinding
import com.example.myapplication.room.database.AppDataBase
import com.example.myapplication.room.entity.UserEntity
import com.example.myapplication.ui.view.LoadingView
import com.example.myapplication.utils.formatTime
import com.example.myapplication.utils.showToast
import com.xw.repo.security.EncryptHelper

class RegisterActivity : BaseActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var loadingView: LoadingView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "注册"

        loadingView = LoadingView(this, R.style.CustomDialog)
        initButton()
    }

    private fun initButton() {
        binding.registerLogin.setOnClickListener {
            myStartActivity(LoginActivity::class.java)
            finish()
            overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out)
        }
        //点击注册按钮
        binding.registerButton.setOnClickListener {
            val userName = binding.registerEt1.text.toString()
            val passWord:String = binding.registerEt2.text.toString()
            val rePassWord = binding.registerEt3.text.toString()
            if (userName.length < 4) {
                binding.registerEt1.error = "用户名不能少于四位"
            }

            if (userName.isBlank()) {
                binding.registerEt1.error = "用户名不能为空"
            }

            if (passWord.isBlank()) {
                binding.registerEt2.error = "密码不能为空"
            }

            if (rePassWord.isBlank()) {
                binding.registerEt3.error = "密码不能为空"
            }

            if(userName.isNotBlank() && passWord.isNotBlank() && rePassWord.isNotBlank() && userName.length >= 4){
                val userDao = AppDataBase.getAppDataBase(this).userDao()
                val encryptHelper = EncryptHelper(this)
                val encrypt = encryptHelper.encrypt(passWord)
                userDao.insert(UserEntity(username = userName, password = encrypt, regtime = formatTime()))
                "注册成功".showToast(this)
                myStartActivity(LoginActivity::class.java)
                finish()
                overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out)
            }
        }
    }
}