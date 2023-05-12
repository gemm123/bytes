package com.example.bytes.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.bytes.data.locale.RegisterDataClass
import com.example.bytes.data.remote.response.RegisterResponse
import com.example.bytes.data.repository.UserRepository
import okhttp3.RequestBody
import retrofit2.http.Body

class RegisterViewModel(private val userRepository: UserRepository): ViewModel() {
    fun userRegister(user: RegisterDataClass): LiveData<String> {
        return userRepository.setUserRegister(user)
    }
}