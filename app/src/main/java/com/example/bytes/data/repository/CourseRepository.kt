package com.example.bytes.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bytes.data.remote.response.*
import com.example.bytes.data.remote.retrofit.ApiService
import okhttp3.internal.notify
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CourseRepository(
    private val apiService: ApiService
) {
    private val listCourse = MutableLiveData<ListCourseResponse>()
    fun getListCourse(token: String): LiveData<ListCourseResponse> {
        val client = apiService.getCourse(token)
        client.enqueue(object : Callback<ListCourseResponse> {
            override fun onResponse(
                call: Call<ListCourseResponse>,
                response: Response<ListCourseResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        listCourse.value = response.body()
                    } else {
                        Log.e("CourseRepository", response.message())
                    }
                }
            }

            override fun onFailure(call: Call<ListCourseResponse>, t: Throwable) {
                Log.e("CourseRepository", "${t.message}")
            }
        })
        return listCourse
    }

    fun getMaterial(courseId: String, token: String): LiveData<MaterialResponse> {
        val material = MutableLiveData<MaterialResponse>()
        val client = apiService.getMaterial(courseId, token)
        client.enqueue(object: Callback<MaterialResponse> {
            override fun onResponse(
                call: Call<MaterialResponse>,
                response: Response<MaterialResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        material.value = response.body()
                    }
                }
            }

            override fun onFailure(call: Call<MaterialResponse>, t: Throwable) {
                Log.e("CourseRepositoryFailure", "${t.message}")
            }
        })
        return material
    }

    fun getQuiz(courseId: String, token: String): LiveData<QuizResponse> {
        val quiz = MutableLiveData<QuizResponse>()
        val client = apiService.getQuiz(courseId, token)
        client.enqueue(object: Callback<QuizResponse> {
            override fun onResponse(call: Call<QuizResponse>, response: Response<QuizResponse>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        quiz.value = response.body()
                    }
                }
            }

            override fun onFailure(call: Call<QuizResponse>, t: Throwable) {
                Log.e("CourseRepositoryFailure", "${t.message}")
            }
        })
        return quiz
    }

    fun getSummary(courseId: String, token: String): LiveData<SummaryResponse> {
        val summary = MutableLiveData<SummaryResponse>()
        val client = apiService.getSummary(courseId, token)
        client.enqueue(object: Callback<SummaryResponse> {
            override fun onResponse(
                call: Call<SummaryResponse>,
                response: Response<SummaryResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        summary.value = response.body()
                    }
                }
            }

            override fun onFailure(call: Call<SummaryResponse>, t: Throwable) {
                Log.e("CourseRepositoryFailure", "${t.message}")
            }
        })
        return summary
    }

    fun likeCourse(token: String, courseId: String) {
        val client = apiService.likeCourse(token, courseId)
        client.enqueue(object: Callback<LikeResponse> {
            override fun onResponse(call: Call<LikeResponse>, response: Response<LikeResponse>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        Log.d("CourseRepositorySuccess", response.body()!!.messaage)
                    } else {
                        Log.d("CourseRepositorySuccess", response.message())
                    }
                }
            }

            override fun onFailure(call: Call<LikeResponse>, t: Throwable) {
                Log.e("CourseRepositoryFailure", "${t.message}")
            }

        })
    }

    fun unlikeCourse(token: String, courseId: String) {
        val client = apiService.unlikeCourse(token, courseId)
        client.enqueue(object: Callback<UnlikeResponse> {
            override fun onResponse(call: Call<UnlikeResponse>, response: Response<UnlikeResponse>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        Log.d("CourseRepositorySuccess", response.body()!!.message)
                    } else {
                        Log.d("CourseRepositorySuccess", response.message())
                    }
                }
            }

            override fun onFailure(call: Call<UnlikeResponse>, t: Throwable) {
                Log.e("CourseRepositoryFailure", "${t.message}")
            }

        })
    }
}