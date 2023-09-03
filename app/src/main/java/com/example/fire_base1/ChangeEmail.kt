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
        Log.d("testing","$receivetext")
        if (receivetext != null) {
            binding.currentEmail.text = "현재 이메일: ${auth.currentUser!!.email}"
            binding.changeEmail.text = "변경 예정 이메일:${receivetext}"
            if(auth.currentUser!!.email==receivetext){
                Log.d("testing","확인용 로그캣")
                dismiss()
                Toast.makeText(requireContext(),"변경을 워하시는 이메일 주소와 현재 이메일이 동일합니다",Toast.LENGTH_LONG).show()
            }
            binding.yesBtn.setOnClickListener {
                if(!binding.checkbox1.isChecked){
                    Toast.makeText(context,"체크박스의 내용을 읽고 체크해주세요",Toast.LENGTH_LONG).show()
                }
                if(binding.checkpass.text.toString()==""||binding.password1.text.toString()==""){
                    Toast.makeText(context,"비밀번호와 비밀번호 확인 모두 입력해주세요",Toast.LENGTH_LONG).show()
                }
                else{
                    if(binding.password1.text.toString()!=binding.checkpass.text.toString()){
                        Toast.makeText(context,"비밀번호를 다시 확인해주세요",Toast.LENGTH_LONG).show()
                    }
                    dismiss()
                }
            }

        }
        if(receivetext==null){
            Toast.makeText(context,"수정 이메일 값이 전송되지 않았습니다",Toast.LENGTH_SHORT).show()
        }
    }
}