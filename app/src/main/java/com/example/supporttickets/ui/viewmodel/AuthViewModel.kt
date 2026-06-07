package com.example.supporttickets.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supporttickets.core.datastore.TokenDataStore
import com.example.supporttickets.core.network.NetworkResult
import com.example.supporttickets.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenDataStore: TokenDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn: StateFlow<Boolean?> = _isLoggedIn

    init {
        checkSession()
    }

    fun checkSession() {
        viewModelScope.launch {
            val token = tokenDataStore.accessToken.firstOrNull()
            _isLoggedIn.value = !token.isNullOrBlank()
        }
    }

    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _uiState.value = AuthUiState(error = "Por favor completa todos los campos.")
            return
        }
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)
            when (val result = authRepository.login(username, password)) {
                is NetworkResult.Success -> _uiState.value = AuthUiState(isSuccess = true)
                is NetworkResult.Error -> _uiState.value = AuthUiState(error = result.message)
                is NetworkResult.Loading -> Unit
            }
        }
    }

    fun register(
        username: String,
        email: String,
        firstName: String,
        lastName: String,
        password: String,
        confirmPassword: String
    ) {
        when {
            username.isBlank() || email.isBlank() || firstName.isBlank() ||
            lastName.isBlank() || password.isBlank() -> {
                _uiState.value = AuthUiState(error = "Por favor completa todos los campos.")
                return
            }
            password != confirmPassword -> {
                _uiState.value = AuthUiState(error = "Las contraseñas no coinciden.")
                return
            }
            password.length < 6 -> {
                _uiState.value = AuthUiState(error = "La contraseña debe tener al menos 6 caracteres.")
                return
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _uiState.value = AuthUiState(error = "El correo electrónico no es válido.")
                return
            }
        }
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)
            when (val result = authRepository.register(username, email, firstName, lastName, password)) {
                is NetworkResult.Success -> {
                    // Auto-login after register
                    when (val loginResult = authRepository.login(username, password)) {
                        is NetworkResult.Success -> _uiState.value = AuthUiState(isSuccess = true)
                        is NetworkResult.Error -> _uiState.value = AuthUiState(
                            error = "Registro exitoso, pero falló el inicio de sesión: ${loginResult.message}"
                        )
                        is NetworkResult.Loading -> Unit
                    }
                }
                is NetworkResult.Error -> _uiState.value = AuthUiState(
                    error = if (result.code == 404)
                        "El registro no está disponible en este servidor. Contacta al administrador."
                    else result.message
                )
                is NetworkResult.Loading -> Unit
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _uiState.value = AuthUiState()
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
