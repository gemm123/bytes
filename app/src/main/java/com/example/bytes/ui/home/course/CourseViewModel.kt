package com.example.bytes.ui.home.course

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.bytes.data.remote.response.ListCourseResponse
import com.example.bytes.data.repository.CourseRepository
import com.example.bytes.data.repository.UserRepository

class CourseViewModel(private val courseRepository: CourseRepository, private val userRepository: UserRepository): ViewModel() {
    fun getToken(): String {
        return userRepository.getAuthToken().toString()
    }

    fun getListCourse(token: String): LiveData<ListCourseResponse> {
        return courseRepository.getListCourse(token)
    }

    fun likeCourse(token: String, courseId: String) {
        courseRepository.likeCourse(token, courseId)
    }

    fun unlikeCourse(token: String, courseId: String) {
        courseRepository.unlikeCourse(token, courseId)
    }
}