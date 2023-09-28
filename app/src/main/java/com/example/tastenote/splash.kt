package com.example.tastenote

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import java.time.Duration

class splash:ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitysplash)
        Handler().postDelayed(Runnable {
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}