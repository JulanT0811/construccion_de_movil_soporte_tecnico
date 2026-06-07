package com.example.supporttickets.data.remote.api

import com.example.supporttickets.data.remote.dto.UserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {
    @GET("users/")
    suspend fun getUsers(): Response<List<UserDto>>

    @GET("users/{id}/")
    suspend fun getUser(@Path("id") id: Int): Response<UserDto>
}
