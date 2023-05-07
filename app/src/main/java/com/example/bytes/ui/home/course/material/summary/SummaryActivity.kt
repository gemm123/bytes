package com.example.bytes.ui.home.course.material.summary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bytes.MainActivity
import com.example.bytes.databinding.ActivitySummaryBinding

class SummaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySummaryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intentToMain = Intent(this, MainActivity::class.java)

        binding.btnClose.setOnClickListener {
            startActivity(intentToMain)
        }
        binding.btnEnd.setOnClickListener {
            startActivity(intentToMain)
        }
    }
}