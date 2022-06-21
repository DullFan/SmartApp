package com.example.myapplication.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.bean.ItemFunctionBean
import com.example.myapplication.bean.Newslist
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.databinding.ItemHomeFunctionBinding
import com.example.myapplication.databinding.ItemHomeNewBinding
import com.example.myapplication.ui.activity.newlist.WebUrlActivity
import com.example.myapplication.ui.activity.recharge.RechargeActivity
import com.example.myapplication.ui.activity.robot.RobotActivity
import com.example.myapplication.ui.activity.user.AllUserActivity
import com.example.myapplication.ui.activity.usermanagement.UserManagementActivity
import com.example.myapplication.utils.KV
import com.example.myapplication.utils.MMKVData
import com.example.myapplication.utils.WEB_VIEW_URL
import com.example.myapplication.utils.showToast
import com.example.wonder.base.BaseRecyclerViewAdapter
import com.google.android.material.tabs.TabLayout
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator


class HomeFragment : BaseFragment() {
    private val viewDataBinding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    val baseAdapter by lazy {
        object : BaseRecyclerViewAdapter<Newslist>(
            R.layout.item_home_new_,
            mutableListOf<Newslist>()
        ) {
            val op = RequestOptions().apply {
                timeout(15 * 1000)
                placeholder(R.drawable.gif1)
                error(R.drawable.gif4)
            }

            override fun onBind(
                rvDataBinding: ViewDataBinding,
                data: Newslist,
                position: Int
            ) {
                rvDataBinding as ItemHomeNewBinding

                Glide.with(requireContext()).load(data.picUrl).apply { op }
                    .into(rvDataBinding.itemHomeNewImage)
                rvDataBinding.itemHomeNewTime.text = data.ctime
                rvDataBinding.itemHomeNewTitle.text = data.title

                rvDataBinding.root.setOnClickListener {
                    Intent(context, WebUrlActivity::class.java).apply {
                        putExtra("title", "新闻详情")
                        putExtra(WEB_VIEW_URL, data.url)
                        startActivity(this)
                    }
                }
            }

        }
    }

    val mutableListOf = mutableListOf("电竞资讯", "垃圾分类", "宠物新闻")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mutableListOf.forEach {
            viewDataBinding.homeTab.addTab(viewDataBinding.homeTab.newTab().setText(it))
        }
        initBanner()
        initFunctionList()
        initNewList()
        initSwipe()

        viewModel.newType01Request()
        viewModel.newType01LiveData.observe(viewLifecycleOwner) {
            baseAdapter.dataList = it.newslist
        }

        viewModel.newType02LiveData.observe(viewLifecycleOwner) {
            baseAdapter.dataList = it.newslist
        }

        viewModel.newType03LiveData.observe(viewLifecycleOwner) {
            baseAdapter.dataList = it.newslist
        }
        return viewDataBinding.root
    }

    private fun initSwipe() {
        viewDataBinding.homeSwipe.setColorSchemeColors(resources.getColor(R.color.theme_color));
        viewDataBinding.homeSwipe.setOnRefreshListener {
            initBanner()
            initFunctionList()
            initNewList()
            initSwipe()
            "刷新成功".showToast(requireContext())
            viewDataBinding.homeSwipe.isRefreshing = false
        }
    }

    private fun initNewList() {

        viewDataBinding.homeTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.text) {
                    mutableListOf[0] -> viewModel.newType01Request()
                    mutableListOf[1] -> viewModel.newType02Request()
                    mutableListOf[2] -> viewModel.newType03Request()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        viewDataBinding.homeRv2.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = baseAdapter
        }
    }

    private fun initFunctionList() {
        val itemFunctionLis = if (KV.decodeInt(MMKVData.LOGIN_ROLE) == 1) {
            mutableListOf(
                ItemFunctionBean("充值中心", R.drawable.gif1),
                ItemFunctionBean("机器人", R.drawable.gif2),
                ItemFunctionBean("用户管理", R.drawable.gif3),
                ItemFunctionBean("暂未开放", R.drawable.gif4),
            )
        } else {
            mutableListOf(
                ItemFunctionBean("充值中心", R.drawable.gif5),
                ItemFunctionBean("机器人", R.drawable.gif6),
                ItemFunctionBean("暂未开放", R.drawable.gif7),
                ItemFunctionBean("暂未开放", R.drawable.gif8),
            )
        }

        viewDataBinding.homeRv1.apply {
            layoutManager = GridLayoutManager(requireContext(), 4)
            adapter = object : BaseRecyclerViewAdapter<ItemFunctionBean>(
                R.layout.item_home_function,
                itemFunctionLis
            ) {
                override fun onBind(
                    rvDataBinding: ViewDataBinding,
                    data: ItemFunctionBean,
                    position: Int
                ) {
                    rvDataBinding as ItemHomeFunctionBinding
                    rvDataBinding.itemHomeImage.setImageResource(data.res)
                    rvDataBinding.itemHomeText.text = data.title
                    rvDataBinding.root.setOnClickListener {
                        if (position == 0) {
                             myStartActivity(RechargeActivity::class.java,"充值中心")
                            return@setOnClickListener
                        }

                        if (position == 1) {
                            myStartActivity(RobotActivity::class.java,"机器人")
                            return@setOnClickListener
                        }

                        if (position == 2 && KV.decodeInt(MMKVData.LOGIN_ROLE) == 1) {
                            myStartActivity(AllUserActivity::class.java,"用户管理")
                            return@setOnClickListener
                        }
                    }
                }
            }
        }
    }

    private fun initBanner() {
        val bannerList = mutableListOf(
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4
        )
        viewDataBinding.homeBanner.apply {
            adapter = object : BannerImageAdapter<Int>(bannerList) {
                override fun onBindView(
                    holder: BannerImageHolder?,
                    data: Int?,
                    position: Int,
                    size: Int
                ) {
                    holder?.imageView?.setImageResource(data!!)
                }
            }
            setIndicator(CircleIndicator(requireContext()))
        }
    }

}