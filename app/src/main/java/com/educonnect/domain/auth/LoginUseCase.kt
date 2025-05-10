package com.educonnect.domain.auth

import com.educonnect.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class LoginUseCase(private val authRepository: AuthRepository) {

    /**
     * Exécute la logique de connexion.
     * @param email L'email de l'utilisateur.
     * @param password Le mot de passe de l'utilisateur.
     * @return Flow<String> - Le message de réponse.
     */
    suspend fun execute(email: String, password: String): Flow<String> = flow {
        try {
            val response = authRepository.login(email, password)
            emit(response)
        } catch (e: HttpException) {
            emit("Erreur HTTP : ${e.message()}")
        } catch (e: IOException) {
            emit("Erreur Réseau : ${e.message}")
        } catch (e: Exception) {
            emit("Erreur Inconnue : ${e.message}")
        }
    }
}

