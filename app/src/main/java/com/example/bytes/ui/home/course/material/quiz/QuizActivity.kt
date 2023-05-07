package com.example.bytes.ui.home.course.material.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.example.bytes.MainActivity
import com.example.bytes.databinding.ActivityQuizBinding
import com.example.bytes.ui.home.course.material.summary.SummaryActivity

class QuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnClose.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.btnContinue.setOnClickListener {
            val intentToQuiz = Intent(this, SummaryActivity::class.java)
            startActivity(intentToQuiz)
        }

        val key = "print()"
        binding.rgAnswer.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButton = group.findViewById<RadioButton>(checkedId)
            if (selectedRadioButton.text == key) {
                Toast.makeText(this, "Benar", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Salah", Toast.LENGTH_SHORT).show()
            }
        }
    }
}