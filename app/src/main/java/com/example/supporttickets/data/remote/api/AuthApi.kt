package com.example.supporttickets.data.remote.api

import com.example.supporttickets.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    // Intentamos con la ruta estándar de SimpleJWT que configuramos
    @POST("auth/token/")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/token/refresh/")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<RefreshTokenResponse>

    @POST("auth/token/blacklist/")
    suspend fun logout(@Body request: LogoutRequest): Response<Unit>

    // El registro en este backend suele estar en el ViewSet de usuarios
    @POST("auth/register/")
    suspend fun register(@Body request: RegisterRequest): Response<UserDto>
}