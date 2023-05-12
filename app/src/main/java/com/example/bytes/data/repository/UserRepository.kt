package com.example.bytes.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bytes.data.locale.AuthPreference
import com.example.bytes.data.locale.LoginDataClass
import com.example.bytes.data.locale.RegisterDataClass
import com.example.bytes.data.remote.response.LoginResponse
import com.example.bytes.data.remote.response.RegisterResponse
import com.example.bytes.data.remote.retrofit.ApiService
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body

class UserRepository(
    private val apiService: ApiService,
    private val authPreference: AuthPreference
) {
    fun setUserLogin(user: LoginDataClass): LiveData<LoginResponse> {
        val loginResponse = MutableLiveData<LoginResponse>()
        val client = apiService.userLogin(user)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    loginResponse.value = response.body()
                } else {
                    loginResponse.value = response.body()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("UserRepository", "onFailure: ${t.message}")
            }

        })
        return loginResponse
    }

    fun setUserRegister(user: RegisterDataClass): LiveData<String> {
//        val registerResponse = MutableLiveData<RegisterResponse>()
        val message = MutableLiveData<String>()
        val client = apiService.userRegister(user)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    message.value = response.body()?.message.toString()
                } else {
                    message.value = response.body()?.message.toString()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e("UserRepository", "onFailure: ${t.message}")
            }

        })
        return message
    }

    fun setAuthToken(token: String) {
        authPreference.setToken(token)
    }

    fun getAuthToken(): String? {
        return authPreference.getToken()
    }

    fun clearAuthToken() {
        authPreference.clearToken()
    }
}