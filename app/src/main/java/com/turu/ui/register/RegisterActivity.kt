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

            var name = binding.nameEditText.text.toString()
            var email = binding.emailEditText.text.toString()
            var password = binding.passwordEditText.text.toString()
            var confirmPassword = binding.confirmPasswordEditText.text.toString()

            if(confirmPassword == password) {
                var registerRequest = RegisterRequest()

                registerRequest.name = name
                registerRequest.email = email
                registerRequest.password = confirmPassword


                registerViewModel.userRegister(registerRequest)
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                binding.confirmPasswordEditText.error = "Must same with password"
            }
        }


    }

    companion object {
        const val TAG = "RegisterActivity"
    }
}