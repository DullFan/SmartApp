package com.example.wonder.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.bean.RobotStringBean
import com.example.myapplication.databinding.RobotLeftBinding
import com.example.myapplication.databinding.RobotRightBinding
import com.example.myapplication.utils.KV
import com.example.myapplication.utils.MMKVData
import com.youth.banner.indicator.RoundLinesIndicator


val LIFT_ROBOT = 0

val RIGHT_ROBOT = 1
class RobotAdapter(_dataList: List<RobotStringBean>, _index: Int = 0) :
    RecyclerView.Adapter<RobotViewHolder>() {


    var dataList = _dataList
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var index = _index
        set(value) {
            notifyItemChanged(field)
            field = value
            notifyItemChanged(index)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RobotViewHolder {
        return RobotViewHolder(
            if (viewType == LIFT_ROBOT) {
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.robot_left,
                    parent,
                    false
                )
            } else {
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.robot_right,
                    parent,
                    false
                )
            }
        )
    }

    override fun getItemViewType(position: Int): Int {
        return dataList.get(position).type
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RobotViewHolder, position: Int) {
        if (dataList.get(position).type == LIFT_ROBOT) {
            holder.viewDataBinding as RobotLeftBinding
            holder.viewDataBinding.name.text = "机器人"
            holder.viewDataBinding.msg.text = dataList[position].msg
        } else {
            holder.viewDataBinding as RobotRightBinding
            holder.viewDataBinding.name.text = KV.decodeString(MMKVData.USER_NAME)
            holder.viewDataBinding.msg.text = dataList[position].msg
        }
    }
}

class RobotViewHolder(val viewDataBinding: ViewDataBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root)