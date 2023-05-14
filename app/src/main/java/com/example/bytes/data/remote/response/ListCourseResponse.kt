package com.example.bytes.data.remote.response

import com.google.gson.annotations.SerializedName

data class ListCourseResponse(

	@field:SerializedName("data")
	val data: ArrayList<DataItem>,

	@field:SerializedName("message")
	val message: String
)

data class DataItem(

	@field:SerializedName("thumbnail")
	val thumbnail: String,

	@field:SerializedName("userLike")
	val userLike: Boolean,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("tag")
	val tag: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("id")
	val id: String
)
