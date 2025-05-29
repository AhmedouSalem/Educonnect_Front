package com.educonnect.repository

import android.util.Log
import com.educonnect.di.NetworkModule
import com.educonnect.model.SalleDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class SalleRepository {

    private val salleService = NetworkModule.salleService

    suspend fun addSalle(
        numero: String,
        capacite: String,
        type: String,
        etage: String,
        batimentCode: String
    ): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val request = SalleDto(numero, capacite, type, etage, batimentCode)
                val response = salleService.addSalle(request)

                when {
                    response.isSuccessful && response.body()?.message == "Salle ajouté avec succès !" -> {
                        Log.d("SalleRepository", "Salle ajoutée avec succès")
                        true
                    }
                    response.code() == 409 -> {
                        Log.e("SalleRepository", "Salle déjà existante")
                        throw Exception("Cette salle existe déjà.")
                    }
                    response.code() == 400 -> {
                        Log.e("SalleRepository", "Bâtiment introuvable")
                        throw Exception("Bâtiment introuvable.")
                    }
                    else -> {
                        Log.e("SalleRepository", "Erreur API : ${response.code()} - ${response.body()?.message}")
                        false
                    }
                }
            } catch (e: HttpException) {
                Log.e("SalleRepository", "Erreur HTTP : ${e.message}")
                false
            } catch (e: IOException) {
                Log.e("SalleRepository", "Erreur Réseau : ${e.message}")
                false
            }
        }
    }

    suspend fun getSallesByBatiment(batimentCode: String): List<String> {
        return withContext(Dispatchers.IO) {
            try {
                val response = salleService.getSallesByBatiment(batimentCode)
                if (response.isSuccessful) {
                    response.body()?.mapNotNull { it.numero } ?: emptyList()
                } else {
                    Log.e("SalleRepository", "Erreur API : ${response.code()}")
                    emptyList()
                }
            } catch (e: IOException) {
                Log.e("SalleRepository", "Erreur Réseau : ${e.message}")
                emptyList()
            }
        }
    }
}
