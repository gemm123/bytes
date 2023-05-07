package com.example.bytes.data.model

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("password")
	val password: String
)
