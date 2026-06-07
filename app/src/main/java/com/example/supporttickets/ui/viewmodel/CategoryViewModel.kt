package com.example.supporttickets.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supporttickets.core.network.NetworkResult
import com.example.supporttickets.data.remote.dto.CategoryDto
import com.example.supporttickets.data.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CategoryUiState(
    val isLoading: Boolean = false,
    val categories: List<CategoryDto> = emptyList(),
    val error: String? = null,
    val successMessage: String? = null
)

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState: StateFlow<CategoryUiState> = _uiState

    init { loadCategories() }

    fun loadCategories() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            when (val result = categoryRepository.getCategories()) {
                is NetworkResult.Success -> _uiState.value = _uiState.value.copy(
                    isLoading = false, categories = result.data
                )
                is NetworkResult.Error -> _uiState.value = _uiState.value.copy(
                    isLoading = false, error = result.message
                )
                is NetworkResult.Loading -> Unit
            }
        }
    }

    fun createCategory(name: String, description: String) {
        if (name.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "El nombre es requerido.")
            return
        }
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = categoryRepository.createCategory(name, description)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false, successMessage = "Categoría creada."
                    )
                    loadCategories()
                }
                is NetworkResult.Error -> _uiState.value = _uiState.value.copy(
                    isLoading = false, error = result.message
                )
                is NetworkResult.Loading -> Unit
            }
        }
    }

    fun updateCategory(id: Int, name: String, description: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = categoryRepository.updateCategory(id, name, description)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false, successMessage = "Categoría actualizada."
                    )
                    loadCategories()
                }
                is NetworkResult.Error -> _uiState.value = _uiState.value.copy(
                    isLoading = false, error = result.message
                )
                is NetworkResult.Loading -> Unit
            }
        }
    }

    fun deleteCategory(id: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = categoryRepository.deleteCategory(id)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false, successMessage = "Categoría eliminada."
                    )
                    loadCategories()
                }
                is NetworkResult.Error -> _uiState.value = _uiState.value.copy(
                    isLoading = false, error = result.message
                )
                is NetworkResult.Loading -> Unit
            }
        }
    }

    fun toggleActive(id: Int) {
        viewModelScope.launch {
            when (val result = categoryRepository.toggleActive(id)) {
                is NetworkResult.Success -> loadCategories()
                is NetworkResult.Error -> _uiState.value = _uiState.value.copy(error = result.message)
                is NetworkResult.Loading -> Unit
            }
        }
    }

    fun clearMessages() {
        _uiState.value = _uiState.value.copy(error = null, successMessage = null)
    }
}
