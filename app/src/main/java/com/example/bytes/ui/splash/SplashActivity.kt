package com.example.bytes.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModelProvider
import com.example.bytes.data.locale.AuthPreference
import com.example.bytes.data.remote.retrofit.ApiConfig
import com.example.bytes.data.repository.UserRepository
import com.example.bytes.databinding.ActivitySplashBinding
import com.example.bytes.ui.login.LoginActivity
import com.example.bytes.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var viewModel: SplashViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setUserFlow()
    }

    private fun setUserFlow() {
        var tokenIsEmpty = true
        viewModel.token.observe(this) { token ->
            if (!token.isNullOrEmpty()) {
                tokenIsEmpty = false
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            if (!tokenIsEmpty) {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)
    }

    private fun setupViewModel() {
        val apiService = ApiConfig.getApiService()
        val authPreferences = AuthPreference(this)
        val userRepository = UserRepository(apiService, authPreferences)
        viewModel = ViewModelProvider(this, SplashViewModelFactory(userRepository))[SplashViewModel::class.java]
    }
}