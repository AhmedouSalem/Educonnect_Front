package com.educonnect.repository

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

/**
 * Modèle de requête pour l'authentification.
 */
data class AuthenticationRequest(
    val username: String,
    val password: String
)

/**
 * Modèle de réponse pour l'authentification.
 */
data class AuthenticationResponse(
    val userId: String,
    val role: String,
    val token: String
)

interface AuthService {

    /**
     * Endpoint pour la connexion de l'utilisateur.
     * @param request Les informations de connexion.
     * @return Response<AuthenticationResponse>
     */
    @POST("authenticate")
    suspend fun login(@Body request: AuthenticationRequest): Response<AuthenticationResponse>
}
