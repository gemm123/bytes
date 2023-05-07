package com.example.bytes.ui.home.course.material.first

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bytes.MainActivity
import com.example.bytes.databinding.ActivityFirstBinding
import com.example.bytes.ui.home.course.material.second.SecondActivity

class FirstActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnClose.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.btnContinue.setOnClickListener {
            val intentToSecondMaterial = Intent(this, SecondActivity::class.java)
            startActivity(intentToSecondMaterial)
        }
    }
}