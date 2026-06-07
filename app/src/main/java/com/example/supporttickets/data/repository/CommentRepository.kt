package com.example.supporttickets.data.repository

import com.example.supporttickets.core.network.NetworkResult
import com.example.supporttickets.core.utils.safeApiCall
import com.example.supporttickets.data.remote.api.CommentApi
import com.example.supporttickets.data.remote.dto.CommentDto
import com.example.supporttickets.data.remote.dto.CreateCommentRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommentRepository @Inject constructor(
    private val commentApi: CommentApi
) {
    suspend fun getComments(ticketId: Int? = null): NetworkResult<List<CommentDto>> =
        safeApiCall { commentApi.getComments(ticketId) }

    suspend fun createComment(ticketId: Int, content: String, isInternal: Boolean = false): NetworkResult<CommentDto> =
        safeApiCall { commentApi.createComment(CreateCommentRequest(ticketId, content, isInternal)) }

    suspend fun deleteComment(id: Int): NetworkResult<Unit> =
        safeApiCall { commentApi.deleteComment(id) }
}
