package com.example.myapplication.base

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.viewmodel.MyViewModel
import com.tencent.mmkv.MMKV

open class BaseActivity : AppCompatActivity() {
    val viewModel: MyViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        MMKV.initialize(this)
        //判断是否有传递参数，有的话显示返回图标，没有标题则显示列表图标
        intent.getStringExtra("title")?.let {
            supportActionBar?.title = it
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                //判断标题是否为空，
                intent.getStringExtra("title")?.let {
                    finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //跳转
    fun <T> myStartActivity(clazz: Class<T>, title: String) {
        Intent(this, clazz).apply {
            putExtra("title", title)
            startActivity(this)
        }
    }

    //跳转
    fun <T> myStartActivity(clazz: Class<T>) {
        Intent(this, clazz).apply {
            startActivity(this)
        }
    }
}