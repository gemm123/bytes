package com.example.bytes.data.remote.response

import com.google.gson.annotations.SerializedName

data class SummaryResponse(

	@field:SerializedName("data")
	val data: SummaryData,

	@field:SerializedName("message")
	val message: String
)

data class SummaryData(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("place")
	val place: Int,

	@field:SerializedName("text")
	val text: String,

	@field:SerializedName("courseId")
	val courseId: String
)
