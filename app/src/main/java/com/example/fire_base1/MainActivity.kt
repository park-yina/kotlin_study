package com.example.fire_base1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.example.fire_base1.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var email: String
    lateinit var password: String
    lateinit var editText: EditText
    lateinit var passwordText: EditText
    var imm:InputMethodManager?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        editText = findViewById(R.id.input_id)
        passwordText = findViewById(R.id.input_password)
        val button = binding.button
        imm=getSystemService(android.content.Context.INPUT_METHOD_SERVICE)as InputMethodManager?
        button.setOnClickListener {
            email = editText.text.toString()
            password = passwordText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            auth.currentUser?.sendEmailVerification()?.addOnCompleteListener{
                                sendTask->
                                if(sendTask.isSuccessful){
                                    Toast.makeText(this,"인증메일을 발송하였습니다",Toast.LENGTH_LONG).show()
                                }
                                else{
                                    Toast.makeText(this,"인증메일 발송에 실패하였습니다",Toast.LENGTH_LONG).show()
                                }
                            }
                        } else {
                            Log.d("testing", "fail", task.exception)
                        }
                    }
            } else {
                Log.d("testing", "empty string")
            }
        }
        binding.button2.setOnClickListener {
            hidekey()
            email = editText.text.toString()
            password = passwordText.text.toString()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "로그인 성공", Toast.LENGTH_LONG).show()
                        auth.currentUser?.let{
                            Log.d("testing","${auth.currentUser!!.isEmailVerified}")
                            Log.d("testing","${auth.currentUser!!.email}")
                            Log.d("testing","${auth.currentUser!!.uid}")

                        }
                    } else {
                        Toast.makeText(this, "로그인 실패", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
    fun hidekey(){
       currentFocus?.let { v->
           imm?.hideSoftInputFromWindow(v.windowToken,0)
       }
    }
}
