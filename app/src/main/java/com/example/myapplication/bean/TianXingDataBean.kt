package com.example.myapplication.bean

data class TianXingDataBean(
    val code: Int,
    val msg: String,
    val newslist: List<Newslist>
)

data class Newslist(
    val ctime: String,
    val description: String,
    val id: String,
    val picUrl: String,
    val source: String,
    val title: String,
    val url: String
)