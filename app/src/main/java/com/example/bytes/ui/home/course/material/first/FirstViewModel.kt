package com.example.bytes.ui.home.course.material.first

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.bytes.data.remote.response.MaterialResponse
import com.example.bytes.data.repository.CourseRepository
import com.example.bytes.data.repository.UserRepository

class FirstViewModel(private val courseRepository: CourseRepository, private val userRepository: UserRepository): ViewModel() {
    fun getToken(): String {
        return userRepository.getAuthToken().toString()
    }

    fun getMaterial(courseId: String, token: String): LiveData<MaterialResponse> {
        return courseRepository.getMaterial(courseId, token)
    }
}