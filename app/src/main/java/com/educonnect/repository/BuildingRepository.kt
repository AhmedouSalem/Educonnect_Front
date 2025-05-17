package com.educonnect.repository

import android.util.Log
import com.educonnect.di.NetworkModule
import com.educonnect.model.BatimentDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class BuildingRepository {

    private val buildingService = NetworkModule.buildingService

    suspend fun addBuilding(code: String, anneC: String, campusNom: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val request = BatimentDto(codeB = code, anneC = anneC, campusNom = campusNom)
                val response = buildingService.addBuilding(request)
                when {
                    response.isSuccessful && response.body()?.message == "Bâtiment ajouté avec succès !" -> {
                        true
                    }
                    response.code() == 409 -> {
                        throw Exception("Ce bâtiment existe déjà.")
                    }
                    response.code() == 400 -> {
                        throw Exception("Campus introuvable.")
                    }
                    else -> {
                        false
                    }
                }
            } catch (e: HttpException) {
                Log.e("BuildingRepository", "Erreur HTTP : ${e.message}")
                false
            } catch (e: IOException) {
                Log.e("BuildingRepository", "Erreur Réseau : ${e.message}")
                false
            }
        }
    }

    suspend fun getAllBatimentCodes(): List<String> {
        return withContext(Dispatchers.IO) {
            try {
                val response = NetworkModule.buildingService.getAllBatiments()
                if (response.isSuccessful) {
                    response.body()?.mapNotNull { it.codeB } ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
}
