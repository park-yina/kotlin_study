package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import org.checkerframework.checker.units.qual.A

class MainActivity : AppCompatActivity() {
    lateinit var firebase: FirebaseAuth
    lateinit var db: FirebaseFirestore
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.signIn.setOnClickListener {
            val intent= Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }
    }
    fun onDialogDimssmiss(resBundle: Bundle?){
        if (resBundle != null) {
            val resData= resBundle.getString("resid").toString()
            if (resData != null) {
                binding.inputId.setText(resData)
            } else {
                Log.d("testing","null")
              binding.inputId.setText("")
            }
        }
            else{
                Toast.makeText(this, "커스텀 다이얼 실행 안됨", Toast.LENGTH_SHORT).show()
            }

    }
}