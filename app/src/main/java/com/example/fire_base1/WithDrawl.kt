package com.example.fire_base1

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fire_base1.databinding.WithdrawlBinding
import com.google.firebase.auth.FirebaseAuth

class WithDrawl:AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=WithdrawlBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= FirebaseAuth.getInstance()
        binding.delbtn3.setOnClickListener {
            val user = auth.currentUser!!
            user.delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "회원탈퇴가 완료되었습니다", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Log.d("testing", "회원 탈퇴에 실패했습니다ㅏ")
                    }
                }
        }
        binding.delbtn1.setOnClickListener {  }
    }
}