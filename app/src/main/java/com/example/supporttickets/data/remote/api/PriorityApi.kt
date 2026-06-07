package com.example.supporttickets.data.remote.api

import com.example.supporttickets.data.remote.dto.CreatePriorityRequest
import com.example.supporttickets.data.remote.dto.PriorityDto
import retrofit2.Response
import retrofit2.http.*

interface PriorityApi {
    @GET("priorities/")
    suspend fun getPriorities(): Response<List<PriorityDto>>

    @POST("priorities/")
    suspend fun createPriority(@Body request: CreatePriorityRequest): Response<PriorityDto>

    @PUT("priorities/{id}/")
    suspend fun updatePriority(@Path("id") id: Int, @Body request: CreatePriorityRequest): Response<PriorityDto>

    @DELETE("priorities/{id}/")
    suspend fun deletePriority(@Path("id") id: Int): Response<Unit>
}
