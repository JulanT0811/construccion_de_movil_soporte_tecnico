package com.example.supporttickets.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CommentDto(
    val id: Int = 0,
    val ticket: Int = 0,
    val content: String = "",
    @SerializedName("is_internal") val isInternal: Boolean = false,
    @SerializedName("author_name") val authorName: String = "",
    @SerializedName("created_at") val createdAt: String = ""
)

data class CreateCommentRequest(
    val ticket: Int,
    val content: String,
    @SerializedName("is_internal") val isInternal: Boolean = false
)
