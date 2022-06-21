package com.example.myapplication.base

import android.content.Intent
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.viewmodel.MyViewModel

open class BaseFragment : Fragment() {

    val viewModel:MyViewModel by viewModels()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                //判断标题是否为空，
                activity?.intent?.getStringExtra("title")?.let {
                    activity?.finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //显示Toast
    fun showToast(content: String) {
        Toast.makeText(context, content, Toast.LENGTH_LONG).show()
    }

    //跳转
    fun <T> myStartActivity(clazz: Class<T>, title: String) {
        Intent(context, clazz).apply {
            putExtra("title", title)
            startActivity(this)
        }
    }

    //跳转
    fun <T> myStartActivity(clazz: Class<T>) {
        Intent(requireContext(), clazz).apply {
            startActivity(this)
        }
    }

    fun showLog(content: String) {
        Log.i("特殊的Log", content)
    }
}