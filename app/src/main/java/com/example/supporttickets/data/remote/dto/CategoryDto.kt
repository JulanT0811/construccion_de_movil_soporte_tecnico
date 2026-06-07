package com.example.supporttickets.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CategoryDto(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    @SerializedName("is_active") val isActive: Boolean = true
)

data class CreateCategoryRequest(
    val name: String,
    val description: String
)
