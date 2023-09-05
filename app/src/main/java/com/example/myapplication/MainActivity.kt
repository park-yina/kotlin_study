package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var firebase: FirebaseAuth
    lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebase = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        if (firebase.currentUser != null) {
            db.collection("users")
                .add(User(firebase.currentUser!!.uid, firebase.currentUser!!.email.toString()))
        }
        else{
            db.collection("users")
                .add(User("name","email"))
        }
    }
}