package com.example.bytes.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)
