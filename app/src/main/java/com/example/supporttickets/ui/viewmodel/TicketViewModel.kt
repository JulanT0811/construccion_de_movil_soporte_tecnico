package com.example.supporttickets.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.supporttickets.data.TicketRepository
import com.example.supporttickets.model.Priority
import com.example.supporttickets.model.Status
import com.example.supporttickets.model.Ticket
import kotlinx.coroutines.flow.StateFlow

class TicketViewModel : ViewModel() {
    val tickets: StateFlow<List<Ticket>> = TicketRepository.tickets

    fun createTicket(title: String, description: String, category: String, priority: Priority) {
        if (title.isBlank() || description.isBlank() || category.isBlank()) return

        val newTicket = Ticket(
            title = title,
            description = description,
            category = category,
            priority = priority
        )
        TicketRepository.addTicket(newTicket)
    }

    fun changeStatus(ticketId: String, nextStatus: Status) {
        TicketRepository.updateTicketStatus(ticketId, nextStatus)
    }
}