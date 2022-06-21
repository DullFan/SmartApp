package com.example.myapplication.utils

import com.tencent.mmkv.MMKV

val KV: MMKV = MMKV.defaultMMKV()

object MMKVData {
    const val USER_NAME = "username"
    const val PASSWORD = "password"

    const val LOGIN_USER_NAME = "LOGIN_USER_NAME"
    const val LOGIN_ROLE = "LOGIN_ROLE"

    const val FLAG_01 = "FLAG_01"
    const val FLAG_02 = "FLAG_02"
}