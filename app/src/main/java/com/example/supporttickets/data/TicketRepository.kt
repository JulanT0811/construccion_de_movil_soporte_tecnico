package com.example.supporttickets.data

import com.example.supporttickets.model.Priority
import com.example.supporttickets.model.Status
import com.example.supporttickets.model.Ticket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object TicketRepository {
    private val _tickets = MutableStateFlow<List<Ticket>>(
        listOf(
            Ticket(title = "Fallo de conexión WiFi", description = "El router del piso 2 no asigna IP.", category = "Redes", priority = Priority.ALTA),
            Ticket(title = "Laptop no enciende", description = "Se apagó repentinamente y no da video.", category = "Hardware", priority = Priority.MEDIA),
            Ticket(title = "Actualización de software", description = "Instalar la última versión de la IDE.", category = "Software", priority = Priority.BAJA, status = Status.EN_PROGRESO)
        )
    )
    val tickets: StateFlow<List<Ticket>> = _tickets

    fun addTicket(ticket: Ticket) {
        _tickets.value = _tickets.value + ticket
    }

    fun updateTicketStatus(ticketId: String, newStatus: Status) {
        _tickets.value = _tickets.value.map {
            if (it.id == ticketId) it.copy(status = newStatus) else it
        }
    }
}