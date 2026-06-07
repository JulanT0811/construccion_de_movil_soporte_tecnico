package com.example.supporttickets.data.repository

import com.example.supporttickets.core.datastore.TokenDataStore
import com.example.supporttickets.core.network.NetworkResult
import com.example.supporttickets.core.utils.safeApiCall
import com.example.supporttickets.data.remote.api.AuthApi
import com.example.supporttickets.data.remote.dto.*
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authApi: AuthApi,
    private val tokenDataStore: TokenDataStore
) {
    suspend fun login(username: String, password: String): NetworkResult<LoginResponse> {
        val result = safeApiCall { authApi.login(LoginRequest(username, password)) }
        if (result is NetworkResult.Success) {
            tokenDataStore.saveTokens(result.data.access, result.data.refresh)
            tokenDataStore.saveUsername(username)
        }
        return result
    }

    suspend fun logout(): NetworkResult<Unit> {
        val refreshToken = tokenDataStore.refreshToken.firstOrNull() ?: ""
        val result = safeApiCall { authApi.logout(LogoutRequest(refreshToken)) }
        tokenDataStore.clearTokens()
        return result
    }

    suspend fun register(
        username: String,
        email: String,
        firstName: String,
        lastName: String,
        password: String
    ): NetworkResult<UserDto> = safeApiCall {
        authApi.register(RegisterRequest(username, email, firstName, lastName, password))
    }
}
