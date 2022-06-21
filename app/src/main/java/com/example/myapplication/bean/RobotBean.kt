package com.example.myapplication.bean

data class RobotBean(
    val code: Int,
    val msg: String,
    val newslist: List<RobotNewslist>
)

data class RobotNewslist(
    val datatype: String,
    val reply: String
)