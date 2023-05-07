package com.example.bytes.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bytes.R
import com.example.bytes.data.model.ApiConfig
import com.example.bytes.data.model.SignUpResponse
import com.example.bytes.databinding.ActivityRegisterBinding
import com.example.bytes.ui.login.LoginActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)
        setupViewModel()
        setupUI()
    }

    private fun setupUI() {
        binding.tvRegisterDescription.setText(Html.fromHtml(getString(R.string.register_description)))

        val username = binding.etUsername.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.btnSignUp.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            val user = SignUpResponse(email, username, password)
            signUp(user)
            Toast.makeText(this, "Sign up button clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signUp(user: SignUpResponse) {
        val client = ApiConfig.getApiService().signUp(user)
        client.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Toast.makeText(this@RegisterActivity, "Success", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Failed", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setupViewModel() {
        val viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
    }
}