package com.example.myapplication.ui.activity.user

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityAllUserBinding
import com.example.myapplication.databinding.ItemUserBinding
import com.example.myapplication.room.database.AppDataBase
import com.example.myapplication.room.entity.UserEntity
import com.example.myapplication.ui.view.MyLinearLayout
import com.example.myapplication.utils.showToast
import com.example.wonder.base.BaseRecyclerViewAdapter

class AllUserActivity : BaseActivity() {
    val viewDataBinding by lazy {
        ActivityAllUserBinding.inflate(layoutInflater)
    }

    val userDao by lazy {
        AppDataBase.getAppDataBase(this).userDao()
    }

    val baseAdapter by lazy {
        val all = userDao.getAll() as ArrayList<UserEntity>
        all.removeAt(0)
        object : BaseRecyclerViewAdapter<UserEntity>(R.layout.item_user, all) {
            override fun onBind(rvDataBinding: ViewDataBinding, data: UserEntity, position: Int) {
                rvDataBinding as ItemUserBinding
                rvDataBinding.name.text = data.username
                rvDataBinding.phone.text = if (data.telephone != "") data.telephone else "未填"
                rvDataBinding.layoutAll.click = object : MyLinearLayout.onClick {
                    override fun onClickListener() {
                        startActivity(
                            Intent(
                                this@AllUserActivity,
                                UserDetailsActivity::class.java
                            ).apply {
                                putExtra("title", "用户详情")
                                putExtra("user", data.username)
                            }
                        )
                    }
                }

                rvDataBinding.del.setOnClickListener {
                    initDialog(data)
                }

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDataBinding.root)
        initData()
    }

    private fun initData() {
        if (userDao.getAll().size - 1 != 0) {
            viewDataBinding.text.visibility = View.GONE
            viewDataBinding.rv.visibility = View.VISIBLE
            viewDataBinding.button.visibility = View.VISIBLE
            viewDataBinding.button.setOnClickListener {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle("提示")
                builder.setMessage("确定要删除全部用户吗?")
                builder.setPositiveButton(
                    "确定"
                ) { dialog, which ->
                    val allList = userDao.getAll() as ArrayList<UserEntity>
                    allList.removeAt(0)
                    allList.forEach {
                        userDao.delete(it)
                    }
                    "删除成功".showToast(this)
                    initData()
                    val all = userDao.getAll() as ArrayList<UserEntity>
                    all.removeAt(0)
                    baseAdapter.dataList = all
                }
                builder.setNegativeButton(
                    "取消"
                ) { dialog, which ->

                }
                builder.show()
            }
            initRv()

        } else {
            viewDataBinding.text.visibility = View.VISIBLE
            viewDataBinding.rv.visibility = View.GONE
            viewDataBinding.button.visibility = View.GONE
        }
    }

    private fun initRv() {
        viewDataBinding.rv.apply {
            layoutManager = LinearLayoutManager(this@AllUserActivity)
            adapter = baseAdapter
        }
    }


    private fun initDialog(userEntity: UserEntity) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("提示")
        builder.setMessage("确定要删除该用户吗?")
        builder.setPositiveButton(
            "确定"
        ) { dialog, which ->
            userDao.delete(userEntity)
            "删除成功".showToast(this)
            val all = userDao.getAll() as ArrayList<UserEntity>
            all.removeAt(0)
            baseAdapter.dataList = all
        }
        builder.setNegativeButton(
            "取消"
        ) { dialog, which ->

        }
        builder.show()
    }
}