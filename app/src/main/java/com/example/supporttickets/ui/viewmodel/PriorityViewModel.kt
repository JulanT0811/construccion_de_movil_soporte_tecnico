package com.example.supporttickets.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supporttickets.core.network.NetworkResult
import com.example.supporttickets.data.remote.dto.CreatePriorityRequest
import com.example.supporttickets.data.remote.dto.PriorityDto
import com.example.supporttickets.data.repository.PriorityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PriorityUiState(
    val isLoading: Boolean = false,
    val priorities: List<PriorityDto> = emptyList(),
    val error: String? = null,
    val successMessage: String? = null
)

@HiltViewModel
class PriorityViewModel @Inject constructor(
    private val priorityRepository: PriorityRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PriorityUiState())
    val uiState: StateFlow<PriorityUiState> = _uiState

    init { loadPriorities() }

    fun loadPriorities() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            when (val result = priorityRepository.getPriorities()) {
                is NetworkResult.Success -> _uiState.value = _uiState.value.copy(
                    isLoading = false, priorities = result.data
                )
                is NetworkResult.Error -> _uiState.value = _uiState.value.copy(
                    isLoading = false, error = result.message
                )
                is NetworkResult.Loading -> Unit
            }
        }
    }

    fun createPriority(level: Int, name: String, description: String, color: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = priorityRepository.createPriority(CreatePriorityRequest(level, name, description, color))) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, successMessage = "Prioridad creada.")
                    loadPriorities()
                }
                is NetworkResult.Error -> _uiState.value = _uiState.value.copy(
                    isLoading = false, error = result.message
                )
                is NetworkResult.Loading -> Unit
            }
        }
    }

    fun deletePriority(id: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = priorityRepository.deletePriority(id)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, successMessage = "Prioridad eliminada.")
                    loadPriorities()
                }
                is NetworkResult.Error -> _uiState.value = _uiState.value.copy(
                    isLoading = false, error = result.message
                )
                is NetworkResult.Loading -> Unit
            }
        }
    }

    fun clearMessages() {
        _uiState.value = _uiState.value.copy(error = null, successMessage = null)
    }
}
