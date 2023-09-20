package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

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
        fun getFirebaseToken(){
            FirebaseMessaging.getInstance().token.addOnCompleteListener(
                OnCompleteListener { task->
                    if(task.isSuccessful) {
                        val token = task.result
                        Log.d("testing","$token")
                    }
                    else{
                        Log.d("testing","fail",task.exception)
                        return@OnCompleteListener
                    }

                })
        }
        getFirebaseToken()
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
                        val intent=Intent(this,ProFile::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this,"로그인에 실패했습니다",Toast.LENGTH_LONG).show()
                    }
                }
        }
        val retrofit = Retrofit.Builder()
            .baseUrl("https://fcm.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(connection::class.java)
        val message = FcmMessage(
            FcmContent(
                token="전송 할 디바이스 토큰",
                notification = FcmNotification(body = "메시지 테스트", title = "fcm message")
            )
        )
        val call = service.sendFcmMessage(message)
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("testing","성공")
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
               Log.d("testing","실패")
            }

        })
    }
    fun onDialogDimssmiss(){
        finish()
    }
}