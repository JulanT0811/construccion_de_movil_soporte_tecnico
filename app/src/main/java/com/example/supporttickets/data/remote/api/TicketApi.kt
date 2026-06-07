package com.example.supporttickets.data.remote.api

import com.example.supporttickets.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.*

interface TicketApi {
    @GET("tickets/")
    suspend fun getTickets(
        @Query("search") search: String? = null,
        @Query("status") status: String? = null,
        @Query("priority") priority: Int? = null,
        @Query("category") category: Int? = null
    ): Response<List<TicketDto>>

    @POST("tickets/")
    suspend fun createTicket(@Body request: CreateTicketRequest): Response<TicketDto>

    @GET("tickets/{id}/")
    suspend fun getTicket(@Path("id") id: Int): Response<TicketDto>

    @PUT("tickets/{id}/")
    suspend fun updateTicket(@Path("id") id: Int, @Body request: CreateTicketRequest): Response<TicketDto>

    @PATCH("tickets/{id}/")
    suspend fun patchTicket(@Path("id") id: Int, @Body request: Map<String, Any>): Response<TicketDto>

    @DELETE("tickets/{id}/")
    suspend fun deleteTicket(@Path("id") id: Int): Response<Unit>

    @POST("tickets/{id}/assign/")
    suspend fun assignTicket(@Path("id") id: Int, @Body request: AssignTicketRequest): Response<TicketDto>

    @POST("tickets/{id}/change_status/")
    suspend fun changeStatus(@Path("id") id: Int, @Body request: ChangeStatusRequest): Response<TicketDto>

    @GET("tickets/{id}/stats/")
    suspend fun getTicketStats(@Path("id") id: Int): Response<TicketStatsDto>

    @GET("tickets/stats/")
    suspend fun getDashboardStats(): Response<TicketStatsDto>
}
