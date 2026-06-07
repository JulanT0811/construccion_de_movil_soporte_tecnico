package com.example.supporttickets.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.supporttickets.ui.screens.auth.LoginScreen
import com.example.supporttickets.ui.screens.auth.RegisterScreen
import com.example.supporttickets.ui.screens.categories.CategoriesScreen
import com.example.supporttickets.ui.screens.home.HomeScreen
import com.example.supporttickets.ui.screens.priorities.PrioritiesScreen
import com.example.supporttickets.ui.screens.splash.SplashScreen
import com.example.supporttickets.ui.screens.tickets.CreateTicketScreen
import com.example.supporttickets.ui.screens.tickets.TicketDetailScreen
import com.example.supporttickets.ui.screens.tickets.TicketsScreen
import com.example.supporttickets.ui.screens.users.UsersScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.SPLASH
    ) {
        composable(NavRoutes.SPLASH) {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate(NavRoutes.HOME) {
                        popUpTo(NavRoutes.SPLASH) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(NavRoutes.LOGIN) {
                        popUpTo(NavRoutes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(NavRoutes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(NavRoutes.HOME) {
                        popUpTo(NavRoutes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(NavRoutes.REGISTER)
                }
            )
        }

        composable(NavRoutes.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(NavRoutes.HOME) {
                        popUpTo(NavRoutes.REGISTER) { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }

        composable(NavRoutes.HOME) {
            HomeScreen(
                onNavigateToTickets = { navController.navigate(NavRoutes.TICKETS) },
                onNavigateToCategories = { navController.navigate(NavRoutes.CATEGORIES) },
                onNavigateToPriorities = { navController.navigate(NavRoutes.PRIORITIES) },
                onNavigateToUsers = { navController.navigate(NavRoutes.USERS) },
                onLogout = {
                    navController.navigate(NavRoutes.LOGIN) {
                        popUpTo(NavRoutes.HOME) { inclusive = true }
                    }
                }
            )
        }

        composable(NavRoutes.TICKETS) {
            TicketsScreen(
                onNavigateToDetail = { ticketId ->
                    navController.navigate(NavRoutes.ticketDetail(ticketId))
                },
                onNavigateToCreate = { navController.navigate(NavRoutes.TICKET_CREATE) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = NavRoutes.TICKET_DETAIL,
            arguments = listOf(navArgument("ticketId") { type = NavType.IntType })
        ) { backStackEntry ->
            val ticketId = backStackEntry.arguments?.getInt("ticketId") ?: return@composable
            TicketDetailScreen(
                ticketId = ticketId,
                onBack = { navController.popBackStack() }
            )
        }

        composable(NavRoutes.TICKET_CREATE) {
            CreateTicketScreen(
                onSuccess = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }

        composable(NavRoutes.CATEGORIES) {
            CategoriesScreen(onBack = { navController.popBackStack() })
        }

        composable(NavRoutes.PRIORITIES) {
            PrioritiesScreen(onBack = { navController.popBackStack() })
        }

        composable(NavRoutes.USERS) {
            UsersScreen(onBack = { navController.popBackStack() })
        }
    }
}
