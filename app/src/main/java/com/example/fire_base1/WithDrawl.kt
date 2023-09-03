package com.example.fire_base1

import android.app.ProgressDialog.show
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import com.example.fire_base1.databinding.WithdrawlBinding
import com.google.firebase.auth.FirebaseAuth

class WithDrawl:AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=WithdrawlBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.changeId.isVisible=false
        binding.curId.isVisible=false
        auth= FirebaseAuth.getInstance()
        binding.delbtn3.setOnClickListener {
            val user = auth.currentUser!!
            user.delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "회원탈퇴가 완료되었습니다", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Log.d("testing", "회원 탈퇴에 실패했습니다")
                    }
                }
        }
        val args=Bundle()
        binding.delbtn1.setOnClickListener {
            binding.changeId.isVisible=true
            binding.curId.isVisible=true;
            binding.curId.text="현재 이메일 : ${auth.currentUser!!.email}"
            val changeEmail=ChangeEmail()
            binding.changeId.setOnEditorActionListener { v, actionId, event ->
                var handled = false
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    args.putString("change_text", binding.changeId.text.toString())
                    changeEmail.arguments = args
                            changeEmail.show(
                                supportFragmentManager, "changeEmail"
                            )
                            handled = true
                    }
                    handled
                }
        }
    }
}