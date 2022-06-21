package com.example.myapplication.ui.fragment

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.bean.ItemFunctionBean
import com.example.myapplication.databinding.FragmentMyBinding
import com.example.myapplication.databinding.ItemHomeFunctionBinding
import com.example.myapplication.ui.activity.login.LoginActivity
import com.example.myapplication.ui.activity.my.AboutActivity
import com.example.myapplication.ui.activity.my.ChangePasswordActivity
import com.example.myapplication.ui.activity.my.FunctionSwitchActivity
import com.example.myapplication.ui.activity.my.PersonalInformationActivity
import com.example.myapplication.utils.KV
import com.example.myapplication.utils.MMKVData.LOGIN_USER_NAME
import com.example.myapplication.utils.showLog
import com.example.wonder.base.BaseRecyclerViewAdapter


class MyFragment : BaseFragment() {

    val viewBinding: FragmentMyBinding by lazy {
        FragmentMyBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initRv()
        initOut()
        viewBinding.myName.text = KV.decodeString(LOGIN_USER_NAME)
        initImage()

        viewBinding.myLayout3.setOnClickListener {
            myStartActivity(AboutActivity::class.java,"关于我们")
        }
        return viewBinding.root
    }

    private val launcherActivity =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            if (it != null) {
                viewBinding.myHead.setImageBitmap(it)
            }
        }
    private val launcherActivity2 =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) {
            if (it != null) {
                Glide.with(requireContext()).load(it).into(viewBinding.myHead)
            }
        }

    var permissions: Array<String> =
        arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

    private val launcherActivity3 =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (it[Manifest.permission.WRITE_EXTERNAL_STORAGE]!! && it[Manifest.permission.CAMERA]!! && it[Manifest.permission.READ_EXTERNAL_STORAGE]!!) {
                initDialog()
            } else {
                showLog("请同意权限，才能使用该功能")
            }

        }

    private fun initImage() {
        viewBinding.myHead.setOnClickListener {
            launcherActivity3.launch(permissions)
        }
    }

    private fun initDialog() {
        val dialog = Dialog(requireContext(), R.style.DialogTheme)
        val view = View.inflate(requireContext(), R.layout.dialog_choice, null)
        dialog.setContentView(view)
        val window: Window = dialog.window!!
        window.setGravity(Gravity.BOTTOM)
        window.setWindowAnimations(R.style.PopupAnimation)
        window.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val camera = view.findViewById<TextView>(R.id.takePhotoBtn)
        camera.setOnClickListener { v1: View? ->
            //调用相机方法
            initcamera()
            dialog.dismiss()
        }
        val album = view.findViewById<TextView>(R.id.pickPhotoBtn)
        album.setOnClickListener { v12: View? ->
            //调用相册方法
            initalbum()
            dialog.dismiss()
        }
        val cancel = view.findViewById<TextView>(R.id.cancelBtn)
        cancel.setOnClickListener { v13: View? ->
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun initalbum() {
        launcherActivity2.launch(arrayOf("image/*"))
    }

    private fun initcamera() {
        launcherActivity.launch(null)
    }

    private fun initRv() {
        val listOf = listOf(
            ItemFunctionBean("个人设置", R.drawable.gif16),
            ItemFunctionBean("修改密码", R.drawable.gif15),
            ItemFunctionBean("功能开关", R.drawable.gif13),
            ItemFunctionBean("暂未开发", R.drawable.gif14)
        )
        viewBinding.myRv.layoutManager = GridLayoutManager(requireContext(), 4)
        viewBinding.myRv.adapter = object :
            BaseRecyclerViewAdapter<ItemFunctionBean>(R.layout.item_home_function, listOf) {
            override fun onBind(
                rvDataBinding: ViewDataBinding,
                data: ItemFunctionBean,
                position: Int
            ) {
                rvDataBinding as ItemHomeFunctionBinding
                rvDataBinding.itemHomeText.text = data.title
                rvDataBinding.itemHomeImage.setImageResource(data.res)
                rvDataBinding.root.setOnClickListener {
                    when (position) {
                        0 -> {
                            myStartActivity(PersonalInformationActivity::class.java, "个人设置")
                        }
                        1 -> {
                            myStartActivity(ChangePasswordActivity::class.java, "修改密码")
                        }
                        2 -> {
                            myStartActivity(FunctionSwitchActivity::class.java, "功能开关")
                        }
                    }
                }
            }
        }
    }

    private fun initOut() {
        viewBinding.myButton.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            builder.setTitle("提示")
            builder.setMessage("确定要退出登录吗?")
            builder.setPositiveButton(
                "确定"
            ) { dialog, which ->
                requireActivity().onBackPressed()
                myStartActivity(LoginActivity::class.java)
            }
            builder.setNegativeButton(
                "取消"
            ) { dialog, which ->

            }
            builder.show()
        }
    }
}