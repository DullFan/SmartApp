package com.example.myapplication.ui.activity.welcome

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.activity.viewModels
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityWelcomeBinding
import com.example.myapplication.room.database.AppDataBase
import com.example.myapplication.room.entity.UserEntity
import com.example.myapplication.ui.activity.MainActivity
import com.example.myapplication.ui.activity.login.LoginActivity
import com.example.myapplication.utils.KV
import com.example.myapplication.utils.MMKVData.FLAG_01
import com.example.myapplication.utils.formatTime
import com.example.myapplication.utils.showLog
import com.example.myapplication.utils.showToast
import com.example.myapplication.viewmodel.MyViewModel
import com.tencent.mmkv.MMKV
import com.xw.repo.security.EncryptHelper
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WelcomeActivity : AppCompatActivity() {

    private val viewDataBinding by lazy {
        ActivityWelcomeBinding.inflate(layoutInflater)
    }

    private val viewModel: MyViewModel by viewModels()

    private var number = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = resources.getColor(android.R.color.transparent)
        setContentView(viewDataBinding.root)

        if(!KV.decodeBool(FLAG_01,true)){
            startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java))
            finish()
            return
        }

        supportActionBar?.hide()
        val userDao = AppDataBase.getAppDataBase(this@WelcomeActivity).userDao()

        //判断admin是否存在
        if(!userDao.findByName("admin")){
            val encryptHelper = EncryptHelper(this)
            val encrypt = encryptHelper.encrypt("123456")
            userDao.insert(UserEntity(username = "admin", password = encrypt, role = 1,regtime = formatTime()))
        }


        //开启逐帧动画
        (viewDataBinding.welcomeGif.drawable as AnimationDrawable).start()

        viewDataBinding.data = viewModel
        viewDataBinding.lifecycleOwner = this

        val sendEmptyMessage = object : Handler(Looper.myLooper()!!) {
            override fun handleMessage(msg: Message) {
                if (number == 0) {
                    startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java))
                    finish()
                } else {
                    viewModel.welcomeTextNumber.value = "跳过 $number"
                    number = number.minus(1)
                    sendEmptyMessageDelayed(1, 1000)
                }
            }
        }

        //设置点击时间
        sendEmptyMessage.sendEmptyMessage(1)
        viewDataBinding.welcomeCard.setOnClickListener {
            number = 0
        }

    }
}