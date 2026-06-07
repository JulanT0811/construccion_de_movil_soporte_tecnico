package com.example.supporttickets.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supporttickets.core.network.NetworkResult
import com.example.supporttickets.data.remote.dto.UserDto
import com.example.supporttickets.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UsersUiState(
    val isLoading: Boolean = false,
    val users: List<UserDto> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UsersUiState())
    val uiState: StateFlow<UsersUiState> = _uiState

    init { loadUsers() }

    fun loadUsers() {
        viewModelScope.launch {
            _uiState.value = UsersUiState(isLoading = true)
            when (val result = userRepository.getUsers()) {
                is NetworkResult.Success -> _uiState.value = UsersUiState(users = result.data)
                is NetworkResult.Error -> _uiState.value = UsersUiState(
                    error = if (result.code == 403)
                        "Acceso restringido. Solo administradores pueden ver usuarios."
                    else result.message
                )
                is NetworkResult.Loading -> Unit
            }
        }
    }
}
