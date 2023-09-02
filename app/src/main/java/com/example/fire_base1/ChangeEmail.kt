package com.example.fire_base1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.fire_base1.databinding.ChangeemailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class ChangeEmail:DialogFragment() {
    lateinit var auth:FirebaseAuth
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
        Log.d("testing","$receivetext")
        if (receivetext != null) {
            binding.currentEmail.text = "현재 이메일: ${auth.currentUser!!.email}"
            binding.changeEmail.text = "변경 예정 이메일:${receivetext}"
            if(receivetext==binding.currentEmail.text){
                Toast.makeText(context,"현재 이메일과 수정된 메일의 주소가 같습니다",Toast.LENGTH_LONG).show()
            }
            binding.yesBtn.setOnClickListener {
                dismiss()
                auth.signOut()

            }

        }
        if(receivetext==null){
            Toast.makeText(context,"수정 이메일 값이 전송되지 않았습니다",Toast.LENGTH_SHORT).show()
        }
    }
}