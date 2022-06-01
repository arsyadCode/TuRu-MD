package com.turu.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.turu.databinding.ActivityRegisterBinding
import com.turu.model.user.RegisterRequest
import com.turu.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerViewModel = RegisterViewModel()

        binding.btnRegister.setOnClickListener {
            var registerRequest = RegisterRequest()

            registerRequest.name = binding.nameEditText.text.toString()
            registerRequest.email = binding.emailEditText.text.toString()
            registerRequest.password = binding.confirmPasswordEditText.text.toString()

            registerViewModel.userRegister(registerRequest)
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


    }

    companion object {
        const val TAG = "RegisterActivity"
    }
}