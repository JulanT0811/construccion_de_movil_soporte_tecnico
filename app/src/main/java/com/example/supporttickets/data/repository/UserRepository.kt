package com.example.supporttickets.data.repository

import com.example.supporttickets.core.network.NetworkResult
import com.example.supporttickets.core.utils.safeApiCall
import com.example.supporttickets.data.remote.api.UserApi
import com.example.supporttickets.data.remote.dto.UserDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userApi: UserApi
) {
    suspend fun getUsers(): NetworkResult<List<UserDto>> =
        safeApiCall { userApi.getUsers() }

    suspend fun getUser(id: Int): NetworkResult<UserDto> =
        safeApiCall { userApi.getUser(id) }
}
