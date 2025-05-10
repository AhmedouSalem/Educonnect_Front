package com.educonnect.model


/**
 * Modèle de requête pour l'authentification.
 */
data class AuthenticationRequest(
    val username: String,
    val password: String
)