package com.example.bytes.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.bytes.R
import com.example.bytes.data.locale.AuthPreference
import com.example.bytes.data.locale.RegisterDataClass
import com.example.bytes.data.remote.retrofit.ApiConfig
import com.example.bytes.data.remote.response.RegisterResponse
import com.example.bytes.data.repository.UserRepository
import com.example.bytes.databinding.ActivityRegisterBinding
import com.example.bytes.ui.login.LoginActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        setupViewModel()
        setupUI()
        setupAction()
    }

    private fun setupAction() {
        binding.btnSignUp.setOnClickListener {
            val email: String = binding.etEmail.text.toString()
            val username: String = binding.etUsername.text.toString()
            val password: String = binding.etPassword.text.toString()
            val user = RegisterDataClass(email, username, password)
            viewModel.userRegister(user).observe(this) { message ->
                Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
                if (message != null) {
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
//            Toast.makeText(this@RegisterActivity, email, Toast.LENGTH_SHORT).show()
        }
        binding.btnBack.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun userRegister(user: RegisterDataClass) {

    }

    private fun setupUI() {
        binding.tvRegisterDescription.setText(Html.fromHtml(getString(R.string.register_description)))
    }

    private fun setupViewModel() {
        val apiService = ApiConfig.getApiService()
        val authPreferences = AuthPreference(this)
        val userRepository = UserRepository(apiService, authPreferences)
        viewModel = ViewModelProvider(this, RegisterViewModelFactory(userRepository))[RegisterViewModel::class.java]
    }
}