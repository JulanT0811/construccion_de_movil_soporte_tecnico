package com.example.supporttickets.ui.navigation

object NavRoutes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val TICKETS = "tickets"
    const val TICKET_DETAIL = "ticket_detail/{ticketId}"
    const val TICKET_CREATE = "ticket_create"
    const val CATEGORIES = "categories"
    const val PRIORITIES = "priorities"
    const val USERS = "users"

    fun ticketDetail(ticketId: Int) = "ticket_detail/$ticketId"
}
