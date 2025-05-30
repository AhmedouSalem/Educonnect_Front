package com.educonnect.repository

import android.util.Log
import com.educonnect.di.NetworkModule
import com.educonnect.model.ParcoursDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class ParcoursRepository {

    private val parcoursService = NetworkModule.parcoursService

    suspend fun getAllParcours(): List<ParcoursDto> {
        return withContext(Dispatchers.IO) {
            try {
                parcoursService.getAllParcours()
            } catch (e: IOException) {
                Log.e("ParcoursRepository", "Erreur Réseau : ${e.message}")
                emptyList()
            } catch (e: Exception) {
                Log.e("ParcoursRepository", "Erreur : ${e.message}")
                emptyList()
            }
        }
    }

    suspend fun getParcoursByMention(mention: String): List<ParcoursDto> {
        return withContext(Dispatchers.IO) {
            try {
                val response = parcoursService.getParcoursByMention(mention)
                if (response.isSuccessful) {
                    response.body() ?: emptyList()
                } else {
                    Log.e("ParcoursRepository", "Erreur API : ${response.code()}")
                    emptyList()
                }
            } catch (e: Exception) {
                Log.e("ParcoursRepository", "Exception : ${e.message}")
                emptyList()
            }
        }
    }

    suspend fun getParcoursByCode(code: String): ParcoursDto = parcoursService.getParcoursByCode(code)

    suspend fun createParcours(dto: ParcoursDto): ParcoursDto = parcoursService.createParcours(dto)

    suspend fun updateParcours(code: String, dto: ParcoursDto): ParcoursDto = parcoursService.updateParcours(code, dto)

    suspend fun deleteParcours(code: String) {
        withContext(Dispatchers.IO) {
            try {
                val response = parcoursService.deleteParcours(code)
                if (!response.isSuccessful) {
                    Log.e("ParcoursRepository", "Erreur lors de la suppression : ${response.code()}")
                    throw Exception("Erreur suppression : ${response.code()}")
                } else {
                    Log.d("ParcoursRepository", "Suppression réussie")
                }
            } catch (e: Exception) {
                Log.e("ParcoursRepository", "Exception suppression : ${e.message}")
                throw e
            }
        }
    }
}

