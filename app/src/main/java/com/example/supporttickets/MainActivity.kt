package com.example.supporttickets.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.supporttickets.model.Priority
import com.example.supporttickets.model.Status
import com.example.supporttickets.model.Ticket
import com.example.supporttickets.ui.viewmodel.TicketViewModel

private val icons: Any

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketListScreen(viewModel: TicketViewModel, onNavigateToCreate: () -> Unit) {
    val tickets by viewModel.tickets.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Tickets de Soporte") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreate) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Ticket")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tickets) { ticket ->
                TicketItem(ticket = ticket, onStatusChange = { nextStatus ->
                    viewModel.changeStatus(ticket.id, nextStatus)
                })
            }
        }
    }
}

@Composable
fun TicketItem(ticket: Ticket, onStatusChange: (Status) -> Unit) {
    val priorityColor = when (ticket.priority) {
        Priority.ALTA -> Color(0xFFD32F2F)
        Priority.MEDIA -> Color(0xFFF57C00)
        Priority.BAJA -> Color(0xFF388E3C)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = ticket.title, style = MaterialTheme.typography.titleLarge)
                SuggestionChip(
                    onClick = {},
                    label = { Text(ticket.priority.name) },
                    colors = SuggestionChipDefaults.suggestionChipColors(labelColor = priorityColor)
                )
            }

            Text(text = "Categoría: ${ticket.category}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = ticket.description, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Estado: ${ticket.status.name}", style = MaterialTheme.typography.bodyMedium)

                if (ticket.status != Status.RESUELTO) {
                    Button(
                        onClick = {
                            val next = if (ticket.status == Status.ABIERTO) Status.EN_PROGRESO else Status.RESUELTO
                            onStatusChange(next)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Text(if (ticket.status == Status.ABIERTO) "Atender" else "Resolver")
                    }
                }
            }
        }
    }
}