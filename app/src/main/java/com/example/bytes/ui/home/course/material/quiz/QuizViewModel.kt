package com.example.bytes.ui.home.course.material.quiz

import androidx.lifecycle.ViewModel
import com.example.bytes.data.repository.CourseRepository
import com.example.bytes.data.repository.UserRepository

class QuizViewModel(private val courseRepository: CourseRepository, private val userRepository: UserRepository): ViewModel() {
    fun getToken(): String {
        return userRepository.getAuthToken().toString()
    }
}