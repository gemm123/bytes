package com.example.bytes.data.remote.response

import com.google.gson.annotations.SerializedName

data class MaterialResponse(

	@field:SerializedName("data")
	val data: MaterialData,

	@field:SerializedName("message")
	val message: String
)

data class MaterialData(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("place")
	val place: Int,

	@field:SerializedName("text")
	val text: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("courseId")
	val courseId: String
)
