package com.example.myapplication.ui.activity

import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.LottieDrawable
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.fragment.HomeFragment
import com.example.myapplication.ui.fragment.MyFragment
import com.example.myapplication.ui.fragment.WeatherFragment
import com.example.myapplication.utils.showToast
import com.example.wonder.adapter.BaseFragmentStateAdapter
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    private val navigationTitleList = arrayListOf(
        "主页",
        "天气",
        "我的"
    )

    private var mPreClickPosition = 0


    private val navigationAnimationList = arrayListOf(
        LottieAnimation.HOME,
        LottieAnimation.WEATHER,
        LottieAnimation.MY,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "主页"
        initBottomNavigationView()
        initViewPager()
    }

    private fun initViewPager() {
        val list = mutableListOf<Fragment>(HomeFragment(), WeatherFragment(), MyFragment())
        binding.mainVp2.adapter = BaseFragmentStateAdapter(this, list)

        binding.mainVp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                handleNavigationItem(binding.mainNav.menu.getItem(position))
                binding.mainNav.menu.getItem(position).isChecked = true
            }
        })
    }

    private fun initBottomNavigationView() {
        binding.mainNav.menu.apply {
            for (i in 0 until navigationTitleList.size) {
                add(Menu.NONE, i, Menu.NONE, navigationTitleList[i])
            }
            setLottieDrawable()
        }
        initListeners()
    }

    private fun handleNavigationItem(item: MenuItem) {
        val currentIcon = item.icon as? LottieDrawable
        currentIcon?.apply {
            playAnimation()
        }

        if (item.itemId != mPreClickPosition) {
            binding.mainNav.menu.findItem(mPreClickPosition).icon =
                getLottieDrawable(
                    navigationAnimationList[mPreClickPosition],
                    binding.mainNav
                )
        }

        when (item.title) {
            navigationTitleList[0] -> {
                binding.mainVp2.currentItem = 0
                supportActionBar?.show()
                supportActionBar?.title = navigationTitleList[0]
            }
            navigationTitleList[1] -> {
                binding.mainVp2.currentItem = 1
                supportActionBar?.show()
                supportActionBar?.title = navigationTitleList[1]
            }
            navigationTitleList[2] -> {
                binding.mainVp2.currentItem = 2
                supportActionBar?.hide()
            }
        }


        mPreClickPosition = item.itemId
    }


    private fun initListeners() {
        binding.mainNav.setOnItemSelectedListener {
            handleNavigationItem(it)
            true
        }

        binding.mainNav.setOnItemReselectedListener {
            handleNavigationItem(it)
        }

        binding.mainNav.selectedItemId = 0

        binding.mainNav.menu.forEach {
            val menuItemView =
                binding.mainNav.findViewById(it.itemId) as BottomNavigationItemView
            menuItemView.setOnLongClickListener {
                true
            }
        }
    }

    private fun Menu.setLottieDrawable() {
        for (i in 0 until navigationAnimationList.size) {
            val item = findItem(i)
            item.icon = getLottieDrawable(navigationAnimationList[i], binding.mainNav)
        }
    }

    private fun getLottieDrawable(
        animation: LottieAnimation,
        bottomNavigationView: BottomNavigationView
    ): LottieDrawable {
        return LottieDrawable().apply {
            val result = LottieCompositionFactory.fromAssetSync(
                bottomNavigationView.context.applicationContext, animation.value
            )
            callback = bottomNavigationView
            composition = result.value
        }
    }

    enum class LottieAnimation(val value: String) {
        HOME("home_lottie.json"),
        WEATHER("weather_lottie.json"),
        MY("my_lottie.json"),
    }


    private var exitTime: Long = 0

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