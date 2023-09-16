package com.example.myapplication

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.storage.FirebaseStorage

class MyFirebaseMessageService :FirebaseMessagingService(){
    lateinit var auth:FirebaseAuth
    lateinit var db:FirebaseFirestore
    lateinit var storage: FirebaseStorage

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("testing","fcm tocken:$token")
        auth= FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()
        storage=FirebaseStorage.getInstance()
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("testing","fcm notifi:${message.notification}")
        Log.d("testing","fcm msg:${message.data}")
    }
}