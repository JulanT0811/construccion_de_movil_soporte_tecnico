package com.example.supporttickets.data.repository

import com.example.supporttickets.core.network.NetworkResult
import com.example.supporttickets.core.utils.safeApiCall
import com.example.supporttickets.data.remote.api.CategoryApi
import com.example.supporttickets.data.remote.dto.CategoryDto
import com.example.supporttickets.data.remote.dto.CreateCategoryRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
    private val categoryApi: CategoryApi
) {
    suspend fun getCategories(): NetworkResult<List<CategoryDto>> =
        safeApiCall { categoryApi.getCategories() }

    suspend fun createCategory(name: String, description: String): NetworkResult<CategoryDto> =
        safeApiCall { categoryApi.createCategory(CreateCategoryRequest(name, description)) }

    suspend fun updateCategory(id: Int, name: String, description: String): NetworkResult<CategoryDto> =
        safeApiCall { categoryApi.updateCategory(id, CreateCategoryRequest(name, description)) }

    suspend fun deleteCategory(id: Int): NetworkResult<Unit> =
        safeApiCall { categoryApi.deleteCategory(id) }

    suspend fun toggleActive(id: Int): NetworkResult<CategoryDto> =
        safeApiCall { categoryApi.toggleActive(id) }
}
