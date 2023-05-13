package com.example.bytes.ui.home.course.material.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bytes.data.repository.CourseRepository
import com.example.bytes.data.repository.UserRepository
import com.example.bytes.ui.home.course.material.quiz.QuizViewModel

class SummaryViewModelFactory(private val courseRepository: CourseRepository, private val userRepository: UserRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SummaryViewModel::class.java)) {
            return SummaryViewModel(courseRepository, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}