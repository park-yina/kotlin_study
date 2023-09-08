package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.myapplication.databinding.SenddialogBinding
import com.google.firebase.auth.FirebaseAuth

class SendDialog:DialogFragment() {
    lateinit var binding:SenddialogBinding
    lateinit var email:String
    lateinit var password:String
    lateinit var auth: FirebaseAuth
    private val args=Bundle()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= SenddialogBinding.inflate(inflater, container, false)
        auth= FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
       val receivetext=arguments?.getString("email")
        password= arguments?.getString("password").toString()
        if(receivetext!=null&&password!=null) {
            binding.emailCheck.text = "이메일 주소:${receivetext}"
            binding.tryEmail.setOnClickListener {
                email = receivetext
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                auth.currentUser?.sendEmailVerification()
                                    ?.addOnCompleteListener { sendTask ->
                                        if (sendTask.isSuccessful) {
                                            Toast.makeText(
                                                requireContext(),
                                                "인증메일을 발송하였습니다",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            dismiss()
                                        } else {
                                            Toast.makeText(
                                                requireContext(),
                                                "인증메일 발송에 실패하였습니다",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                            }
                        }
                }
            }
        }
    }
    override fun onStop(){
        super.onStop()
        val intent= Intent(requireActivity(),MainActivity::class.java)
        startActivity(intent)
        val mainActivity=activity as MainActivity
        mainActivity.onDialogDimssmiss()

    }
}