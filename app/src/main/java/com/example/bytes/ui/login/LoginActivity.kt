package com.example.bytes.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.bytes.ui.main.MainActivity
import com.example.bytes.R
import com.example.bytes.data.locale.AuthPreference
import com.example.bytes.data.locale.LoginDataClass
import com.example.bytes.data.remote.retrofit.ApiConfig
import com.example.bytes.data.repository.UserRepository
import com.example.bytes.databinding.ActivityLoginBinding
import com.example.bytes.ui.register.RegisterActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        setupUI()
        setupAction()
    }

    private fun setupAction() {
        binding.apply {
            btnLogin.setOnClickListener {
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                val user = LoginDataClass(email, password)
                var token = ""
                viewModel.userLogin(user).observe(this@LoginActivity) { response ->
                    if (response?.message == "success") {
                        token = response.token
                        viewModel.saveToken(token)
                        Toast.makeText(this@LoginActivity, response?.message.toString(), Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
            btnSignUp.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setupUI() {
        binding.tvLoginDesc.setText(Html.fromHtml(getString(R.string.login_desc)))
    }

    private fun setupViewModel() {
        val apiService = ApiConfig.getApiService()
        val authPreferences = AuthPreference(this)
        val userRepository = UserRepository(apiService, authPreferences)
        viewModel = ViewModelProvider(this, LoginViewModelFactory(userRepository)).get(LoginViewModel::class.java)
    }
}