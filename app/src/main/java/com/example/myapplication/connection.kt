package com.example.myapplication

import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
    interface connection {
        @POST("https://fcm.googleapis.com/v1/projects/androidtest-dfc2a/messages:send")
        @Headers(
            "Content-type:application/json",
            "Authorization:Bearer fcm토큰값"
        )
        fun sendFcmMessage(@Body message: FcmMessage): Call<ResponseBody>
    }

    data class FcmMessage(
        val message: FcmContent
    )

    data class FcmContent(
        val token: String,
        val notification: FcmNotification
    )

    data class FcmNotification(
        val body: String,
        val title: String
    )
