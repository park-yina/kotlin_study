package com.example.myapplication

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.storage.FirebaseStorage

class MyFirebaseMessageService :FirebaseMessagingService(){
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("testing","fcm tocken:$token")
        val preference=this.getSharedPreferences("token", Context.MODE_PRIVATE)
        val edi=preference.edit()
        edi.putString("token",token).apply()
        edi.commit()
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("testing","from:${message.from}")
        if(message.data.isNotEmpty()){
            Log.d("testing","fcm msg:${message.data}")
          Log.d("testing","${message.data["body"].toString()}")
        }
        Log.d("testing","fcm notifi:${message.notification}")
        sendNotification(message)

    }
    private fun sendNotification(message: RemoteMessage){
        val remotecode:Int=101
        val channelName:String="channel1"
        val intent=Intent(this,MainActivity::class.java)
        val pedingIntent=PendingIntent.getService(this,remotecode,intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_MUTABLE)
        val sound=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuild=NotificationCompat.Builder(this,channelName)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(message.data["body"].toString())
            .setContentText(message.data["title"].toString())
            .setAutoCancel(true)
            .setSound(sound)
            .setContentIntent(pedingIntent)
        val notificationmanager=getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            //오레오보다 최신 버전이면 채널 세팅을 따로 해주어야함
            val channel=NotificationChannel(channelName,"Notification_test",NotificationManager.IMPORTANCE_DEFAULT)
            notificationmanager.createNotificationChannel(channel)

        }
        notificationmanager.notify(remotecode,notificationBuild.build())
    }
}