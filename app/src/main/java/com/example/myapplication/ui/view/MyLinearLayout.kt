package com.example.myapplication.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.example.myapplication.utils.showLog

class MyLinearLayout(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount != 2) {
            throw RuntimeException("child count must be 2")
        }
        mMsgView = getChildAt(0)
        mMenuView = getChildAt(1)
    }

    private var time: Long = 0

    private var mMsgView: View? = null
    private var mMenuView: View? = null
    private var showMenu = false
    private var isMoving = false
    private var xOffset = 0
    var x = 0.0
    private var menuWidth = 0
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        menuWidth = mMenuView!!.getWidth()
        relayout()
    }

    private fun relayout() {
        if (!isMoving) {
            //停止的时候 就两种状态 展开菜单 不展开菜单 直接固定值
            if (showMenu) {
                mMsgView!!.layout(-menuWidth, 0, width - menuWidth, height)
                mMenuView!!.layout(width - menuWidth, 0, width, height)
            } else {
                mMsgView!!.layout(0, 0, width, height)
                mMenuView!!.layout(width, 0, width + menuWidth, height)
            }
        } else {
            var msgL = 0
            var msgR = 0
            //右移 要考虑之前的状态是开菜单还是关
            if (xOffset > 0) {
                if (showMenu) {
                    msgL = Math.min(-menuWidth + xOffset, 0)
                    msgR = width + msgL
                } else {
                    msgR = width
                }
            } else if (xOffset < 0) {
                //左移
                if (showMenu) {
                    msgL = -menuWidth
                    msgR = width - menuWidth
                } else {
                    xOffset = Math.max(-menuWidth, xOffset)
                    msgL = xOffset
                    msgR = width + msgL
                }
            } else { //不动时
                msgL = if (showMenu) -menuWidth else 0
                msgR = if (showMenu) width - menuWidth else width
            }
            mMsgView!!.layout(msgL, 0, msgR, height)
            mMenuView!!.layout(
                msgR, 0,
                menuWidth + msgR, height
            )
        }
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (mMenuView != null && mMsgView != null) {
            parent.requestDisallowInterceptTouchEvent(true)
            resolveTouchEvent(ev)
        }
        return true
    }

    interface onClick {
        //需要什么参数自己传递
        fun onClickListener()
    }

    var click: onClick? = null
        set(value) {
            field = value
        }

    private fun resolveTouchEvent(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                time = System.currentTimeMillis()
                isMoving = true
                x = event.x.toDouble()
            }
            MotionEvent.ACTION_MOVE -> {
                val cx = event.x
                xOffset = (cx - x).toInt()
                relayout()
            }
            MotionEvent.ACTION_UP -> {
                if(System.currentTimeMillis() - time < 75){
                    (System.currentTimeMillis() - time).showLog()
                    click!!.onClickListener()
                }else{
                    if (xOffset > menuWidth / 3) {
                        showMenu = false
                    } else if (xOffset < menuWidth / 3) {
                        showMenu = true
                    }
                }
                xOffset = 0
                x = 0.0
                isMoving = false
                time = 0
                relayout()
            }
            else -> {}
        }
    }
}