package com.example.supporttickets.core.utils

object Constants {
    const val BASE_URL = "https://tixi-soporte.uaeftt-ute.site/api/"
    const val DATASTORE_NAME = "auth_prefs"
    
    // Endpoints específicos si se necesitan fuera de Retrofit
    object Endpoints {
        const val LOGIN = "auth/token/"
        const val REFRESH = "auth/token/refresh/"
        const val REGISTER = "users/"
    }
}
