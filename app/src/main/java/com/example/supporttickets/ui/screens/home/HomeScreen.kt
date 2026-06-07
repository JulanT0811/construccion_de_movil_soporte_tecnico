package com.example.supporttickets.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.supporttickets.ui.viewmodel.AuthViewModel
import com.example.supporttickets.ui.viewmodel.TicketsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToTickets: () -> Unit,
    onNavigateToCategories: () -> Unit,
    onNavigateToPriorities: () -> Unit,
    onNavigateToUsers: () -> Unit,
    onLogout: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel(),
    ticketsViewModel: TicketsViewModel = hiltViewModel()
) {
    val ticketsUiState by ticketsViewModel.uiState.collectAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Cerrar Sesión") },
            text = { Text("¿Estás seguro que deseas cerrar sesión?") },
            confirmButton = {
                TextButton(onClick = {
                    authViewModel.logout()
                    onLogout()
                }) { Text("Cerrar Sesión", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) { Text("Cancelar") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Soporte Técnico", style = MaterialTheme.typography.titleLarge)
                        Text("Dashboard", style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f))
                    }
                },
                actions = {
                    IconButton(onClick = { showLogoutDialog = true }) {
                        Icon(Icons.AutoMirrored.Filled.Logout, "Cerrar Sesión")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Stats Section
            Text(
                "Resumen de Tickets",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            val stats = ticketsUiState.stats
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "Total",
                    value = stats?.total ?: ticketsUiState.tickets.size,
                    icon = Icons.Default.ConfirmationNumber,
                    color = MaterialTheme.colorScheme.primary
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "Abiertos",
                    value = stats?.open ?: ticketsUiState.tickets.count { it.status == "open" },
                    icon = Icons.Default.FiberNew,
                    color = Color(0xFF1565C0)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "En Progreso",
                    value = stats?.inProgress ?: ticketsUiState.tickets.count { it.status == "in_progress" },
                    icon = Icons.Default.Sync,
                    color = Color(0xFFE65100)
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "Resueltos",
                    value = stats?.resolved ?: ticketsUiState.tickets.count { it.status == "resolved" },
                    icon = Icons.Default.CheckCircle,
                    color = Color(0xFF2E7D32)
                )
            }

            HorizontalDivider()

            // Quick Actions
            Text(
                "Acciones Rápidas",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            QuickActionCard(
                title = "Tickets",
                subtitle = "Ver y gestionar todos los tickets",
                icon = Icons.Default.ConfirmationNumber,
                color = MaterialTheme.colorScheme.primary,
                onClick = onNavigateToTickets
            )
            QuickActionCard(
                title = "Categorías",
                subtitle = "Gestionar categorías de soporte",
                icon = Icons.Default.Category,
                color = Color(0xFF00695C),
                onClick = onNavigateToCategories
            )
            QuickActionCard(
                title = "Prioridades",
                subtitle = "Configurar niveles de prioridad",
                icon = Icons.Default.PriorityHigh,
                color = Color(0xFFE65100),
                onClick = onNavigateToPriorities
            )
            QuickActionCard(
                title = "Usuarios",
                subtitle = "Ver usuarios del sistema",
                icon = Icons.Default.Group,
                color = Color(0xFF6A1B9A),
                onClick = onNavigateToUsers
            )
        }
    }
}

@Composable
private fun StatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: Int,
    icon: ImageVector,
    color: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(28.dp))
            Text(
                text = value.toString(),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun QuickActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = color.copy(alpha = 0.15f),
                    modifier = Modifier.fillMaxSize()
                ) {}
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(28.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(text = subtitle, style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
