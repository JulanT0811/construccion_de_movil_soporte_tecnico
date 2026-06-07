package com.example.supporttickets.data.remote.dto

data class PriorityDto(
    // Backend may return either 'id' or use 'level' as key
    val id: Int = 0,
    val level: Int = 0,
    val name: String = "",
    val description: String = "",
    val color: String = "#607D8B"
) {
    // Use id if available, otherwise fall back to level
    val effectiveId: Int get() = if (id != 0) id else level
}

data class CreatePriorityRequest(
    val level: Int,
    val name: String,
    val description: String,
    val color: String
)
