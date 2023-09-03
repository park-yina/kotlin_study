package com.example.fire_base1

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.example.fire_base1.databinding.ChangeemailBinding
import com.example.fire_base1.databinding.WithdrawlBinding
import com.google.android.play.integrity.internal.t
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class ChangeEmail:DialogFragment() {
    lateinit var auth:FirebaseAuth
    lateinit var email:String
    lateinit var password:String
    lateinit var textView: TextView
    lateinit var binding:ChangeemailBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=ChangeemailBinding.inflate(inflater,container,false)
        auth= FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val receivetext = arguments?.getString("change_text")
        if (receivetext != null) {
            binding.currentEmail.text = "현재 이메일: ${auth.currentUser!!.email}"
            binding.changeEmail.text = "변경 예정 이메일:${receivetext}"
            if (auth.currentUser!!.email == receivetext) {
                dismiss()
                Toast.makeText(
                    requireContext(),
                    "변경을 원하시는 이메일 주소와 현재 이메일이 동일합니다",
                    Toast.LENGTH_LONG
                ).show()
            }
            binding.yesBtn.setOnClickListener {
                val args=Bundle()
                if (!binding.checkbox1.isChecked) {
                    Toast.makeText(context, "체크박스의 내용을 읽고 체크해주세요", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                if (binding.checkpass.text.toString() != "" && binding.password1.text.toString() != "") {
                    if (binding.password1.text.toString() != binding.checkpass.text.toString()) {
                        Toast.makeText(context, "비밀번호를 다시 확인해주세요", Toast.LENGTH_LONG).show()
                    } else {
                        auth.signOut()
                            email = receivetext
                            password = binding.checkpass.text.toString()
                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(WithDrawl()) { task ->
                                    if (task.isSuccessful) {
                                        auth.currentUser?.sendEmailVerification()
                                            ?.addOnCompleteListener { sendTask ->
                                                if (sendTask.isSuccessful) {
                                                    Toast.makeText(
                                                        requireContext(),
                                                        "인증메일을 발송하였습니다",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                    val intent=Intent(requireContext(),MainActivity::class.java)
                                                    startActivity(intent)
                                                    dismiss()

                                                } else {
                                                    Toast.makeText(
                                                        requireContext(),
                                                        "인증메일 발송에 실패하였습니다",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                }
                                            }
                                    } else {
                                        Log.d("testing", "fail", task.exception)
                                    }
                                }

                    }
                }
                else{
                    Toast.makeText(context, "비밀번호와 비밀번호 확인 모두 입력해주세요", Toast.LENGTH_LONG).show()
                }

                    }

            }
        else{
            Toast.makeText(context,"전달된 이메일 값이 없습니다",Toast.LENGTH_LONG).show()
        }
    }
}