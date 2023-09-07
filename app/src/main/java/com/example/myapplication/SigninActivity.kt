package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivitySigninBinding

class SignInActivity:AppCompatActivity() {
    lateinit var binding: ActivitySigninBinding
    lateinit var email:String
    lateinit var password:String
    private val args=Bundle()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding= ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.sendBtn.setOnClickListener {
            if (binding.signPassword.text.toString() != binding.checkPassword.text.toString()) {
                Toast.makeText(this, "비밀번호를 다시 확인해주세요", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (binding.inputEmail.text.toString() == "" ||
                binding.signPassword.text.toString() == "" ||
                binding.checkPassword.text.toString() == ""
            ) {
                Toast.makeText(this, "입력란을 모두 작성해주세요", Toast.LENGTH_LONG).show()
            } else {
                val sending = SendDialog()
                args.putString("email", binding.inputEmail.text.toString())
                args.putString("password",binding.signPassword.text.toString())
                sending.arguments = args // 프래그먼트의 인자로 설정
                sending.show(
                    supportFragmentManager, "send"
                )
            }
        }
    }

}