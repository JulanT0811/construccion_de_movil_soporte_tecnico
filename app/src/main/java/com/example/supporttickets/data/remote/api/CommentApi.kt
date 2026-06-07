package com.example.supporttickets.data.remote.api

import com.example.supporttickets.data.remote.dto.CommentDto
import com.example.supporttickets.data.remote.dto.CreateCommentRequest
import retrofit2.Response
import retrofit2.http.*

interface CommentApi {
    @GET("comments/")
    suspend fun getComments(@Query("ticket") ticketId: Int? = null): Response<List<CommentDto>>

    @POST("comments/")
    suspend fun createComment(@Body request: CreateCommentRequest): Response<CommentDto>

    @PUT("comments/{id}/")
    suspend fun updateComment(@Path("id") id: Int, @Body request: CreateCommentRequest): Response<CommentDto>

    @DELETE("comments/{id}/")
    suspend fun deleteComment(@Path("id") id: Int): Response<Unit>
}
