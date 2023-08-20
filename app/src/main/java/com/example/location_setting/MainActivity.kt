package com.example.location_setting

import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.location_setting.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val manager=getSystemService(LOCATION_SERVICE)as LocationManager
        var result="ALL Providers:"
        val providers=manager.allProviders
        for(i in providers){
            result+="$i,"
        }
        Log.d("test","result")
    }
}