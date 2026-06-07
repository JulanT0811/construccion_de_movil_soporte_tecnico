package com.example.supporttickets.ui.screens.priorities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.supporttickets.data.remote.dto.PriorityDto
import com.example.supporttickets.ui.components.*
import com.example.supporttickets.ui.viewmodel.PriorityViewModel

@Composable
fun PrioritiesScreen(
    onBack: () -> Unit,
    viewModel: PriorityViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showCreateDialog by remember { mutableStateOf(false) }
    var deletingId by remember { mutableStateOf<Int?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.error) {
        uiState.error?.let { snackbarHostState.showSnackbar(it); viewModel.clearMessages() }
    }
    LaunchedEffect(uiState.successMessage) {
        uiState.successMessage?.let { snackbarHostState.showSnackbar(it); viewModel.clearMessages() }
    }

    if (showCreateDialog) {
        PriorityDialog(
            onConfirm = { level, name, desc, color ->
                viewModel.createPriority(level, name, desc, color)
                showCreateDialog = false
            },
            onDismiss = { showCreateDialog = false }
        )
    }

    deletingId?.let { id ->
        ConfirmDeleteDialog(
            title = "Eliminar Prioridad",
            message = "¿Estás seguro que deseas eliminar esta prioridad?",
            onConfirm = { viewModel.deletePriority(id); deletingId = null },
            onDismiss = { deletingId = null }
        )
    }

    Scaffold(
        topBar = { AppTopBar(title = "Prioridades", onBack = onBack) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showCreateDialog = true }) {
                Icon(Icons.Default.Add, null)
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        when {
            uiState.isLoading -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) { items(4) { SkeletonCard() } }
            }
            uiState.priorities.isEmpty() -> EmptyState("No hay prioridades registradas")
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.priorities) { priority ->
                        PriorityCard(
                            priority = priority,
                            onDelete = { deletingId = priority.effectiveId }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PriorityCard(priority: PriorityDto, onDelete: () -> Unit) {
    val color = try {
        Color(android.graphics.Color.parseColor(priority.color))
    } catch (e: Exception) { Color(0xFF607D8B) }

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(color.copy(alpha = 0.15f), shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    priority.level.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(priority.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(color, shape = CircleShape)
                    )
                }
                if (priority.description.isNotBlank()) {
                    Text(
                        priority.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    priority.color,
                    style = MaterialTheme.typography.labelSmall,
                    color = color
                )
            }

            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, "Eliminar", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
private fun PriorityDialog(
    onConfirm: (Int, String, String, String) -> Unit,
    onDismiss: () -> Unit
) {
    var level by remember { mutableStateOf("1") }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("#607D8B") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nueva Prioridad") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = level,
                    onValueChange = { level = it.filter { c -> c.isDigit() } },
                    label = { Text("Nivel (número)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre *") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = color,
                    onValueChange = { color = it },
                    label = { Text("Color (hex, ej: #FF5722)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val lvl = level.toIntOrNull() ?: 1
                    if (name.isNotBlank()) onConfirm(lvl, name, description, color)
                },
                enabled = name.isNotBlank()
            ) { Text("Guardar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}
