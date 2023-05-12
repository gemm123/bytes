package com.example.bytes.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bytes.data.locale.LoginDataClass
import com.example.bytes.data.remote.response.LoginResponse
import com.example.bytes.data.repository.UserRepository

class ProfileViewModel(private val userRepository: UserRepository): ViewModel() {

    fun clearToken() {
        userRepository.clearAuthToken()
    }
}