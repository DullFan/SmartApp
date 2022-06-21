package com.example.myapplication.ui.activity.usermanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityUserManagementBinding

class UserManagementActivity : AppCompatActivity() {
    val viewDataBinding by lazy {
        ActivityUserManagementBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDataBinding.root)





    }
}