package com.educonnect.repository

import android.util.Log
import com.educonnect.di.NetworkModule
import com.educonnect.model.AuthenticationRequest
import com.educonnect.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class AuthRepository(private val sessionManager: SessionManager) {

    private val authService = NetworkModule.authService

    /**
     * Effectue la requête de connexion.
     */
    suspend fun login(email: String, password: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val request = AuthenticationRequest(username = email, password = password)
                val response = authService.login(request)

                if (response.isSuccessful) {
                    val body = response.body()

                    body?.let {
                        sessionManager.saveUserData(it)
                        val role = it.role.lowercase()
                        Log.d("AuthRepository", "Role reçu : $role")
                        return@withContext role
                    }

                }

                "Erreur : ${response.code()} - ${response.message()}"

            } catch (e: HttpException) {
                "Erreur HTTP : ${e.message}"
            } catch (e: IOException) {
                "Erreur réseau : ${e.message}"
            } catch (e: Exception) {
                "Erreur inconnue : ${e.message}"
            }
        }
    }
}

