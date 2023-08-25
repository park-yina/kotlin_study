package com.example.fire_base1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fire_base1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}