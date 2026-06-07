package com.example.supporttickets.data.remote.api

import com.example.supporttickets.data.remote.dto.CategoryDto
import com.example.supporttickets.data.remote.dto.CreateCategoryRequest
import retrofit2.Response
import retrofit2.http.*

interface CategoryApi {
    @GET("categories/")
    suspend fun getCategories(): Response<List<CategoryDto>>

    @POST("categories/")
    suspend fun createCategory(@Body request: CreateCategoryRequest): Response<CategoryDto>

    @PUT("categories/{id}/")
    suspend fun updateCategory(@Path("id") id: Int, @Body request: CreateCategoryRequest): Response<CategoryDto>

    @DELETE("categories/{id}/")
    suspend fun deleteCategory(@Path("id") id: Int): Response<Unit>

    @POST("categories/{id}/toggle_active/")
    suspend fun toggleActive(@Path("id") id: Int): Response<CategoryDto>
}
