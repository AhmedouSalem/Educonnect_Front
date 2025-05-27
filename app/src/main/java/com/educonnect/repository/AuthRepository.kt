package com.educonnect.repository

import com.educonnect.di.NetworkModule
import com.educonnect.model.AuthenticationRequest
import com.educonnect.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class AuthRepository(sessionManager: SessionManager) {

    private val authService = NetworkModule.authService

    /**
     * Effectue la requête de connexion via Retrofit.
     * @param email L'email de l'utilisateur.
     * @param password Le mot de passe de l'utilisateur.
     * @return String - Le message de réponse.
     */
    suspend fun login(email: String, password: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val request = AuthenticationRequest(username = email, password = password)
                val response = authService.login(request)

                if (response.isSuccessful) {
                    val body = response.body()
                    "Connexion réussie : ${body?.userId}"
                } else {
                    val errorMessage = when (response.code()) {
                        401 -> "Identifiants incorrects."
                        403 -> "Accès refusé."
                        else -> "Erreur : ${response.code()} - ${response.message()}"
                    }
                    errorMessage
                }

            } catch (e: HttpException) {
                "Erreur HTTP : ${e.message()}"
            } catch (e: IOException) {
                "Erreur réseau : ${e.message}"
            } catch (e: Exception) {
                "Erreur inconnue : ${e.message}"
            }
        }
    }
}
