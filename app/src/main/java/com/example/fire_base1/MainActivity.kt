package com.example.fire_base1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.example.fire_base1.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var email: String
    lateinit var password: String
    lateinit var editText: EditText
    lateinit var passwordText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        editText = findViewById(R.id.input_id)
        passwordText = findViewById(R.id.input_password)
        val button = binding.button

        button.setOnClickListener {
            email = editText.text.toString()
            password = passwordText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Log.d("testing", "task success")
                        } else {
                            Log.d("testing", "fail", task.exception)
                        }
                    }
            } else {
                Log.d("testing", "empty string")
            }
        }
    }
}
