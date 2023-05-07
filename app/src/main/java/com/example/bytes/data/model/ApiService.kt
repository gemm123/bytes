package com.example.bytes.data.model

import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @POST("auth/register")
    fun signUp(
        @Body user: SignUpResponse
    ): Call<ResponseBody>
}