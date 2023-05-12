package com.example.bytes.data.locale

import com.google.gson.annotations.SerializedName

data class RegisterDataClass (
    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("password")
    val password: String
)