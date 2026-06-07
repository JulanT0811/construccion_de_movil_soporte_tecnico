package com.example.supporttickets.data.repository

import com.example.supporttickets.core.network.NetworkResult
import com.example.supporttickets.core.utils.safeApiCall
import com.example.supporttickets.data.remote.api.TicketApi
import com.example.supporttickets.data.remote.dto.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TicketRepository @Inject constructor(
    private val ticketApi: TicketApi
) {
    suspend fun getTickets(
        search: String? = null,
        status: String? = null,
        priority: Int? = null,
        category: Int? = null
    ): NetworkResult<List<TicketDto>> = safeApiCall {
        ticketApi.getTickets(search, status, priority, category)
    }

    suspend fun getTicket(id: Int): NetworkResult<TicketDto> =
        safeApiCall { ticketApi.getTicket(id) }

    suspend fun createTicket(request: CreateTicketRequest): NetworkResult<TicketDto> =
        safeApiCall { ticketApi.createTicket(request) }

    suspend fun updateTicket(id: Int, request: CreateTicketRequest): NetworkResult<TicketDto> =
        safeApiCall { ticketApi.updateTicket(id, request) }

    suspend fun deleteTicket(id: Int): NetworkResult<Unit> =
        safeApiCall { ticketApi.deleteTicket(id) }

    suspend fun assignTicket(id: Int, userId: Int): NetworkResult<TicketDto> =
        safeApiCall { ticketApi.assignTicket(id, AssignTicketRequest(userId)) }

    suspend fun changeStatus(id: Int, status: String): NetworkResult<TicketDto> =
        safeApiCall { ticketApi.changeStatus(id, ChangeStatusRequest(status)) }

    suspend fun getDashboardStats(): NetworkResult<TicketStatsDto> =
        safeApiCall { ticketApi.getDashboardStats() }
}
