package com.example.supporttickets.data.repository

import com.example.supporttickets.core.network.NetworkResult
import com.example.supporttickets.core.utils.safeApiCall
import com.example.supporttickets.data.remote.api.PriorityApi
import com.example.supporttickets.data.remote.dto.CreatePriorityRequest
import com.example.supporttickets.data.remote.dto.PriorityDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PriorityRepository @Inject constructor(
    private val priorityApi: PriorityApi
) {
    suspend fun getPriorities(): NetworkResult<List<PriorityDto>> =
        safeApiCall { priorityApi.getPriorities() }

    suspend fun createPriority(request: CreatePriorityRequest): NetworkResult<PriorityDto> =
        safeApiCall { priorityApi.createPriority(request) }

    suspend fun updatePriority(id: Int, request: CreatePriorityRequest): NetworkResult<PriorityDto> =
        safeApiCall { priorityApi.updatePriority(id, request) }

    suspend fun deletePriority(id: Int): NetworkResult<Unit> =
        safeApiCall { priorityApi.deletePriority(id) }
}
