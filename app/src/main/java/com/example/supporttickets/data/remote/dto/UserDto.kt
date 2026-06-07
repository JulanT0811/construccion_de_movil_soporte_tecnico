package com.example.supporttickets.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    val id: Int = 0,
    val username: String = "",
    val email: String = "",
    @SerializedName("first_name") val firstName: String = "",
    @SerializedName("last_name") val lastName: String = "",
    @SerializedName("is_staff") val isStaff: Boolean = false
)
