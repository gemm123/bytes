package com.example.bytes.ui.home.course.material.second

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bytes.ui.main.MainActivity
import com.example.bytes.databinding.ActivitySecondBinding
import com.example.bytes.ui.home.course.material.quiz.QuizActivity

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnClose.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.btnContinue.setOnClickListener {
            val intentToQuiz = Intent(this, QuizActivity::class.java)
            startActivity(intentToQuiz)
        }
    }
}