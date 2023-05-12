package com.example.bytes.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.bytes.data.locale.LoginDataClass
import com.example.bytes.data.remote.response.LoginResponse
import com.example.bytes.data.repository.UserRepository
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.http.Body

class LoginViewModel(private val userRepository: UserRepository): ViewModel() {

    fun userLogin(user: LoginDataClass): LiveData<LoginResponse> {
        return userRepository.setUserLogin(user)
    }

    fun saveToken(token: String) {
        userRepository.setAuthToken(token)
    }
}