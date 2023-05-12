package com.example.bytes.data.locale

import com.google.gson.annotations.SerializedName

data class LoginDataClass (
    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("password")
    val password: String
)