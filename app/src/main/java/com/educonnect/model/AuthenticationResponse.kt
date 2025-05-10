package com.educonnect.model

/**
 * Modèle de réponse pour l'authentification.
 */
data class AuthenticationResponse(
    val userId: Long,
    val role: String,
    val token: String
)