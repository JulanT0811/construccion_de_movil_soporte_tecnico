package com.example.supporttickets.core.utils

import com.example.supporttickets.core.network.NetworkResult
import retrofit2.Response

suspend fun <T> safeApiCall(call: suspend () -> Response<T>): NetworkResult<T> {
    return try {
        val response = call()
        when {
            response.isSuccessful && response.body() != null -> {
                NetworkResult.Success(response.body()!!)
            }
            response.isSuccessful -> {
                NetworkResult.Error("Respuesta vacía del servidor", response.code())
            }
            response.code() == 401 -> NetworkResult.Error("No autorizado. Por favor inicia sesión.", 401)
            response.code() == 403 -> NetworkResult.Error("Acceso denegado.", 403)
            response.code() == 404 -> NetworkResult.Error("Recurso no encontrado.", 404)
            response.code() == 500 -> NetworkResult.Error("Error interno del servidor.", 500)
            else -> {
                val errorBody = response.errorBody()?.string() ?: "Error desconocido"
                NetworkResult.Error(errorBody, response.code())
            }
        }
    } catch (e: java.net.SocketTimeoutException) {
        NetworkResult.Error("Tiempo de conexión agotado. Verifica tu red.")
    } catch (e: java.net.UnknownHostException) {
        NetworkResult.Error("Sin conexión a internet. Verifica tu red.")
    } catch (e: Exception) {
        NetworkResult.Error(e.localizedMessage ?: "Error de red inesperado.")
    }
}

fun statusToLabel(status: String): String = when (status) {
    "open" -> "Abierto"
    "in_progress" -> "En Progreso"
    "on_hold" -> "En Espera"
    "resolved" -> "Resuelto"
    "closed" -> "Cerrado"
    "cancelled" -> "Cancelado"
    else -> status
}

fun statusToColor(status: String): Long = when (status) {
    "open" -> 0xFF1565C0
    "in_progress" -> 0xFFE65100
    "on_hold" -> 0xFF6A1B9A
    "resolved" -> 0xFF2E7D32
    "closed" -> 0xFF37474F
    "cancelled" -> 0xFFB71C1C
    else -> 0xFF757575
}
