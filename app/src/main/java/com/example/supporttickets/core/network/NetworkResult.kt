package com.example.supporttickets.core.network

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(val message: String, val code: Int? = null) : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>()
}

fun <T> NetworkResult<T>.onSuccess(action: (T) -> Unit): NetworkResult<T> {
    if (this is NetworkResult.Success) action(data)
    return this
}

fun <T> NetworkResult<T>.onError(action: (String, Int?) -> Unit): NetworkResult<T> {
    if (this is NetworkResult.Error) action(message, code)
    return this
}
