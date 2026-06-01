package com.example.supporttickets.model

import java.util.UUID

enum class Priority { BAJA, MEDIA, ALTA }
enum class Status { ABIERTO, EN_PROGRESO, RESUELTO }

data class Ticket(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val category: String,
    val priority: Priority,
    val status: Status = Status.ABIERTO,
    val createdAt: String = "Hoy"
)