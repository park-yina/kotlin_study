package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import org.checkerframework.checker.units.qual.A

class MainActivity : AppCompatActivity() {
    lateinit var firebase: FirebaseAuth
    lateinit var email:String
    lateinit var password:String
    lateinit var db: FirebaseFirestore
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebase= FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()
    }

    override fun onStart() {
        super.onStart()
        binding.signIn.setOnClickListener {
            val intent= Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }
        binding.button2.setOnClickListener {
            if(binding.inputId.text.toString()==""||binding.inputPassword.text.toString()==""){
                Toast.makeText(this,"아이디와 비밀번호를 모두 입력헤주세요",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            email=binding.inputId.text.toString()
            password=binding.inputPassword.text.toString()
            firebase.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){task->
                    if(task.isSuccessful){
                        Toast.makeText(this,"${firebase.currentUser!!.email}님 로그인 성공",Toast.LENGTH_LONG).show()
                        val user=User("${firebase.currentUser!!.uid}","${firebase.currentUser!!.email}")
                        db.collection("users")
                            .document("cur_user")
                            .set(user)
                        val intent=Intent(this,ProFile::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this,"로그인에 실패했습니다",Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
    fun onDialogDimssmiss(){
        finish()
    }
}