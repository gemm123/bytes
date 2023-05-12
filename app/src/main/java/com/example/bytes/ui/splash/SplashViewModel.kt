package com.example.bytes.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bytes.data.repository.UserRepository

class SplashViewModel(private val userRepository: UserRepository): ViewModel() {
    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    init {
        _token.value = userRepository.getAuthToken()
    }
}