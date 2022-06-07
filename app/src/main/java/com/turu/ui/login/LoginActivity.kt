package com.turu.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.turu.MainActivity
import com.turu.R
import com.turu.databinding.ActivityLoginBinding
import com.turu.model.UserModel
import com.turu.model.UserPreference
import com.turu.model.user.LoginRequest
import com.turu.ui.ViewModelFactory
import com.turu.ui.register.RegisterActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var user: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(
            this,ViewModelFactory(UserPreference.getInstance(dataStore))
        )[LoginViewModel::class.java]
        loginViewModel.getUser().observe(this) { user ->
            this.user = user
        }

        binding.btnLogin.setOnClickListener {
            var loginRequest = LoginRequest()
            loginRequest.email = binding.emailEditText.text.toString()
            loginRequest.password = binding.passwordEditText.text.toString()

            loginViewModel.userLogin(loginRequest)

            if(user.isLogin) {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

    }

    companion object {
        const val TAG = "LoginActivity"
    }
}