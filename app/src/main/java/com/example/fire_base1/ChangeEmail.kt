package com.example.fire_base1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.fire_base1.databinding.ChangeemailBinding

class ChangeEmail:DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding=ChangeemailBinding.inflate(inflater,container,false)
        return binding.root
    }

}