package com.example.tastenote

data class ApiResonse(val resultcode:String,
    val response:NaverUser)
data class NaverUser(
    val id:String,
    val nickname:String
)
