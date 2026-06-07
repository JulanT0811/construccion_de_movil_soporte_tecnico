package com.example.supporttickets.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TicketDto(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val status: String = "open",
    val category: Int? = null,
    @SerializedName("category_name") val categoryName: String? = null,
    val priority: Int? = null,
    @SerializedName("priority_name") val priorityName: String? = null,
    @SerializedName("priority_color") val priorityColor: String? = null,
    @SerializedName("assigned_to") val assignedTo: Int? = null,
    @SerializedName("assigned_to_name") val assignedToName: String? = null,
    @SerializedName("created_by") val createdBy: Int? = null,
    @SerializedName("created_by_name") val createdByName: String? = null,
    @SerializedName("created_at") val createdAt: String = "",
    @SerializedName("updated_at") val updatedAt: String = ""
)

data class CreateTicketRequest(
    val title: String,
    val description: String,
    val category: Int,
    val priority: Int
)

data class AssignTicketRequest(
    @SerializedName("user_id") val userId: Int
)

data class ChangeStatusRequest(
    val status: String
)

data class TicketStatsDto(
    val total: Int = 0,
    val open: Int = 0,
    @SerializedName("in_progress") val inProgress: Int = 0,
    val resolved: Int = 0,
    @SerializedName("on_hold") val onHold: Int = 0,
    val closed: Int = 0,
    val cancelled: Int = 0
)

data class PaginatedResponse<T>(
    val count: Int = 0,
    val next: String? = null,
    val previous: String? = null,
    val results: List<T> = emptyList()
)
