package com.example.bytes.data.remote.retrofit

import com.example.bytes.data.locale.LoginDataClass
import com.example.bytes.data.locale.RegisterDataClass
import com.example.bytes.data.remote.response.LoginResponse
import com.example.bytes.data.remote.response.RegisterResponse
import com.example.bytes.data.remote.response.UserResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("auth/register")
    fun userRegister(
        @Body user: RegisterDataClass
    ): Call<RegisterResponse>

    @POST("auth/login")
    fun userLogin(
        @Body user: LoginDataClass
    ): Call<LoginResponse>

    @GET("auth/user")
    fun getUser(
        @Header("Authorization") token: String
    ): Call<UserResponse>
}