package com.example.schoolexeat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.schoolexeat.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitBtn.setOnClickListener {
            if(binding.userNameTiTv.text.toString() == "user" && binding.passwordTiTv.text.toString() == "1234"){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else{
                Snackbar.make(it, "Invalid login attempt", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.btnGuestUser.setOnClickListener {
            val intent = Intent(this, ExeatInfo::class.java)
            startActivity(intent)
        }
    }
}