package com.example.supporttickets.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supporttickets.core.network.NetworkResult
import com.example.supporttickets.data.remote.dto.*
import com.example.supporttickets.data.repository.CategoryRepository
import com.example.supporttickets.data.repository.CommentRepository
import com.example.supporttickets.data.repository.PriorityRepository
import com.example.supporttickets.data.repository.TicketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TicketsUiState(
    val isLoading: Boolean = false,
    val tickets: List<TicketDto> = emptyList(),
    val selectedTicket: TicketDto? = null,
    val stats: TicketStatsDto? = null,
    val comments: List<CommentDto> = emptyList(),
    val categories: List<CategoryDto> = emptyList(),
    val priorities: List<PriorityDto> = emptyList(),
    val error: String? = null,
    val successMessage: String? = null,
    val searchQuery: String = "",
    val statusFilter: String? = null
)

@HiltViewModel
class TicketsViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val categoryRepository: CategoryRepository,
    private val priorityRepository: PriorityRepository,
    private val commentRepository: CommentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TicketsUiState())
    val uiState: StateFlow<TicketsUiState> = _uiState

    init {
        loadTickets()
        loadCategories()
        loadPriorities()
        loadDashboardStats()
    }

    fun loadTickets(search: String? = null, status: String? = null) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            when (val result = ticketRepository.getTickets(search, status)) {
                is NetworkResult.Success -> _uiState.value = _uiState.value.copy(
                    isLoading = false, tickets = result.data
                )
                is NetworkResult.Error -> _uiState.value = _uiState.value.copy(
                    isLoading = false, error = result.message
                )
                is NetworkResult.Loading -> Unit
            }
        }
    }

    fun loadTicketDetail(id: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            when (val result = ticketRepository.getTicket(id)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false, selectedTicket = result.data
                    )
                    loadComments(id)
                }
                is NetworkResult.Error -> _uiState.value = _uiState.value.copy(
                    isLoading = false, error = result.message
                )
                is NetworkResult.Loading -> Unit
            }
        }
    }

    fun createTicket(title: String, description: String, categoryId: Int, priorityId: Int) {
        if (title.isBlank() || description.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "El título y descripción son requeridos.")
            return
        }
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            when (val result = ticketRepository.createTicket(
                CreateTicketRequest(title, description, categoryId, priorityId)
            )) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false, successMessage = "Ticket creado exitosamente."
                    )
                    loadTickets()
                    loadDashboardStats()
                }
                is NetworkResult.Error -> _uiState.value = _uiState.value.copy(
                    isLoading = false, error = result.message
                )
                is NetworkResult.Loading -> Unit
            }
        }
    }

    fun changeStatus(ticketId: Int, status: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = ticketRepository.changeStatus(ticketId, status)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        selectedTicket = result.data,
                        successMessage = "Estado actualizado."
                    )
                    loadTickets()
                    loadDashboardStats()
                }
                is NetworkResult.Error -> _uiState.value = _uiState.value.copy(
                    isLoading = false, error = result.message
                )
                is NetworkResult.Loading -> Unit
            }
        }
    }

    fun deleteTicket(id: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = ticketRepository.deleteTicket(id)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false, successMessage = "Ticket eliminado."
                    )
                    loadTickets()
                    loadDashboardStats()
                }
                is NetworkResult.Error -> _uiState.value = _uiState.value.copy(
                    isLoading = false, error = result.message
                )
                is NetworkResult.Loading -> Unit
            }
        }
    }

    fun loadComments(ticketId: Int) {
        viewModelScope.launch {
            when (val result = commentRepository.getComments(ticketId)) {
                is NetworkResult.Success -> _uiState.value = _uiState.value.copy(
                    comments = result.data
                )
                else -> Unit
            }
        }
    }

    fun addComment(ticketId: Int, content: String, isInternal: Boolean = false) {
        if (content.isBlank()) return
        viewModelScope.launch {
            when (val result = commentRepository.createComment(ticketId, content, isInternal)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        comments = _uiState.value.comments + result.data,
                        successMessage = "Comentario agregado."
                    )
                }
                is NetworkResult.Error -> _uiState.value = _uiState.value.copy(error = result.message)
                is NetworkResult.Loading -> Unit
            }
        }
    }

    fun loadCategories() {
        viewModelScope.launch {
            when (val result = categoryRepository.getCategories()) {
                is NetworkResult.Success -> _uiState.value = _uiState.value.copy(
                    categories = result.data
                )
                else -> Unit
            }
        }
    }

    fun loadPriorities() {
        viewModelScope.launch {
            when (val result = priorityRepository.getPriorities()) {
                is NetworkResult.Success -> _uiState.value = _uiState.value.copy(
                    priorities = result.data
                )
                else -> Unit
            }
        }
    }

    fun loadDashboardStats() {
        viewModelScope.launch {
            when (val result = ticketRepository.getDashboardStats()) {
                is NetworkResult.Success -> _uiState.value = _uiState.value.copy(stats = result.data)
                else -> Unit
            }
        }
    }

    fun updateSearch(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        loadTickets(search = query.ifBlank { null }, status = _uiState.value.statusFilter)
    }

    fun updateStatusFilter(status: String?) {
        _uiState.value = _uiState.value.copy(statusFilter = status)
        loadTickets(search = _uiState.value.searchQuery.ifBlank { null }, status = status)
    }

    fun clearMessages() {
        _uiState.value = _uiState.value.copy(error = null, successMessage = null)
    }
}
