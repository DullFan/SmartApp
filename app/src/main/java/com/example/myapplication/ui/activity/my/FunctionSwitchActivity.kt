package com.example.myapplication.ui.activity.my

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityFunctionSwitchBinding
import com.example.myapplication.utils.KV
import com.example.myapplication.utils.MMKVData
import com.example.myapplication.utils.showLog

class FunctionSwitchActivity : BaseActivity() {
    val viewDataBinding by lazy {
        ActivityFunctionSwitchBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDataBinding.root)

        viewDataBinding.switch1.isChecked = KV.decodeBool(MMKVData.FLAG_01,true)
        viewDataBinding.switch2.isChecked = KV.decodeBool(MMKVData.FLAG_02,true)

       viewDataBinding.switch1.setOnCheckedChangeListener { buttonView, isChecked ->
           if(isChecked){
                KV.encode(MMKVData.FLAG_01,true)
           }else{
               KV.encode(MMKVData.FLAG_01,false)
           }
       }

        viewDataBinding.switch2.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                KV.encode(MMKVData.FLAG_02,true)
            }else{
                KV.encode(MMKVData.FLAG_02,false)
            }
        }
    }
}