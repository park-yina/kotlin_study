package com.example.tastenote

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header


interface NaverRetrofit {
    @GET("https://openapi.naver.com/v1/nid/me")
    fun getUserInfo(@Header("Authorization") authorization:String): Call<ResponseBody>
}