package com.example.supporttickets.core.network

import com.example.supporttickets.core.datastore.TokenDataStore
import com.example.supporttickets.data.remote.api.AuthApi
import com.example.supporttickets.data.remote.dto.RefreshTokenRequest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(
    private val tokenDataStore: TokenDataStore,
    // Provider to avoid circular dependency with Retrofit
    private val authApiProvider: Provider<AuthApi>
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        // Avoid infinite refresh loops
        if (response.request.header("Authorization") == null) return null
        if (responseCount(response) >= 2) return null

        val refreshToken = runBlocking { tokenDataStore.refreshToken.firstOrNull() } ?: return null

        return runBlocking {
            try {
                val result = authApiProvider.get().refreshToken(RefreshTokenRequest(refreshToken))
                if (result.isSuccessful && result.body() != null) {
                    val newAccess = result.body()!!.access
                    tokenDataStore.saveTokens(newAccess, refreshToken)
                    response.request.newBuilder()
                        .header("Authorization", "Bearer $newAccess")
                        .build()
                } else {
                    tokenDataStore.clearTokens()
                    null
                }
            } catch (e: Exception) {
                null
            }
        }
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var prior = response.priorResponse
        while (prior != null) {
            count++
            prior = prior.priorResponse
        }
        return count
    }
}
