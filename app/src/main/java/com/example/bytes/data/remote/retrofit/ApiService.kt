package com.example.bytes.data.remote.retrofit

import com.example.bytes.data.locale.LoginDataClass
import com.example.bytes.data.locale.RegisterDataClass
import com.example.bytes.data.remote.response.*
import com.example.bytes.ui.home.course.material.summary.SummaryFragment
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

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

    @GET("course")
    fun getCourse(
        @Header("Authorization") token: String
    ): Call<ListCourseResponse>

    @GET("course/material/{courseId}")
    fun getMaterial(
        @Path("courseId") courseId: String,
        @Header("Authorization") token: String
    ): Call<MaterialResponse>

    @GET("course/quiz/{courseId}")
    fun getQuiz(
        @Path("courseId") courseId: String,
        @Header("Authorization") token: String
    ): Call<QuizResponse>

    @GET("course/summary/{courseId}")
    fun getSummary(
        @Path("courseId") courseId: String,
        @Header("Authorization") token: String
    ): Call<SummaryResponse>

    @POST("like/{courseId}")
    fun likeCourse(
        @Header("Authorization") token: String,
        @Path("courseId") courseId: String
    ): Call<LikeResponse>

    @DELETE("like/{courseId}")
    fun unlikeCourse(
        @Header("Authorization") token: String,
        @Path("courseId") courseId: String
    ): Call<UnlikeResponse>
}