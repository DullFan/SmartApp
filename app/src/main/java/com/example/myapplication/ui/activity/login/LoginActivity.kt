package com.example.myapplication.ui.activity.login

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.room.database.AppDataBase
import com.example.myapplication.ui.activity.MainActivity
import com.example.myapplication.ui.view.LoadingView
import com.example.myapplication.utils.KV
import com.example.myapplication.utils.MMKVData.LOGIN_ROLE
import com.example.myapplication.utils.MMKVData.LOGIN_USER_NAME
import com.example.myapplication.utils.MMKVData.PASSWORD
import com.example.myapplication.utils.MMKVData.USER_NAME
import com.example.myapplication.utils.showLog
import com.example.myapplication.utils.showToast
import com.xw.repo.security.EncryptHelper

class LoginActivity : BaseActivity() {
    val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private var exitTime: Long = 0

    private lateinit var loadingView: LoadingView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.title = "登录"
        loadingView = LoadingView(this, R.style.CustomDialog)

        val decodeString: String = KV.decodeString(USER_NAME, "")
        val passwordDecode: String = KV.decodeString(PASSWORD, "")
        if (decodeString != "") {
            binding.loginEt1.setText(decodeString)
            binding.loginEt2.setText(passwordDecode)
            binding.loginCheck.isChecked = true
        }
        initButton()
    }

    private fun initButton() {
        binding.loginRegister.setOnClickListener {
            myStartActivity(RegisterActivity::class.java)
            finish()
            overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out)
        }

        binding.loginButton.setOnClickListener {
            val userName = binding.loginEt1.text.toString()
            val passWord = binding.loginEt2.text.toString()
            if (userName.isBlank()) {
                binding.loginEt1.error = "用户名不能为空"
            }

            if (passWord.isBlank()) {
                binding.loginEt2.error = "密码不能为空"
            }

            val findByNameReturnUserEntity =
                AppDataBase.getAppDataBase(this).userDao().findByNameReturnUserEntity(userName)
            if (findByNameReturnUserEntity == null) {
                "请查看用户名和密码是否正确".showToast(this)
            } else {
                val encryptHelper = EncryptHelper(this)
                val decrypt = encryptHelper.decrypt(findByNameReturnUserEntity.password);
                if (decrypt == passWord) {
                    if (binding.loginCheck.isChecked) {
                        KV.encode(USER_NAME, userName)
                        KV.encode(PASSWORD, passWord)
                    } else {
                        KV.removeValueForKey(USER_NAME)
                        KV.removeValueForKey(PASSWORD)
                    }
                    KV.encode(LOGIN_USER_NAME, userName)
                    KV.encode(LOGIN_ROLE, findByNameReturnUserEntity.role)
                    finish()
                    myStartActivity(MainActivity::class.java)
                    "登录成功".showToast(this)
                } else {
                    "请查看用户名和密码是否正确".showToast(this)
                }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit()
            return false
        }
        return super.onKeyDown(keyCode, event)
    }

    fun exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            "再按一次退出程序".showToast(this)
            exitTime = System.currentTimeMillis();
        } else {
            onBackPressed();
            System.exit(0);
        }
    }
}