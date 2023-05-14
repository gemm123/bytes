package com.example.bytes.data.remote.response

import com.google.gson.annotations.SerializedName

data class QuizResponse(

	@field:SerializedName("data")
	val data: QuizData,

	@field:SerializedName("message")
	val message: String
)

data class QuizData(

	@field:SerializedName("question")
	val question: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("place")
	val place: Int,

	@field:SerializedName("choice")
	val choice: String,

	@field:SerializedName("courseId")
	val courseId: String,

	@field:SerializedName("key")
	val key: String
)
