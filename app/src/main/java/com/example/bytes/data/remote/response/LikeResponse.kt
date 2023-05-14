package com.example.bytes.data.remote.response

import android.view.textclassifier.ConversationActions.Message
import com.google.gson.annotations.SerializedName

data class LikeResponse(
    @field:SerializedName("message")
    val messaage: String
)

data class UnlikeResponse(
    @field:SerializedName("message")
    val message: String
)
