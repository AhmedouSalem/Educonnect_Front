package com.educonnect.repository

import android.util.Log
import com.educonnect.di.NetworkModule
import com.educonnect.model.BatimentDto
import com.educonnect.model.BatimentUpdatedRequest
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

    suspend fun getAllBuildings(): List<BatimentDto> {
        return withContext(Dispatchers.IO) {
            try {
                val response = buildingService.getAllBatiments()
                if (response.isSuccessful) {
                    response.body() ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                emptyList()
            }
        }
    }


    suspend fun getBatimentsByCampus(campus: String): List<String> {
        return withContext(Dispatchers.IO) {
            try {
                val response = buildingService.getBatimentsByCampus(campus)
                if (response.isSuccessful) {
                    response.body()?.mapNotNull { it.codeB } ?: emptyList()
                } else {
                    Log.e("BatimentRepository", "Erreur API : ${response.code()}")
                    emptyList()
                }
            } catch (e: IOException) {
                Log.e("BatimentRepository", "Erreur Réseau : ${e.message}")
                emptyList()
            }
        }
    }

    suspend fun updateBuilding(oldBuilding: BatimentDto, newBuilding: BatimentDto): Boolean {
        val batimentUpdatedRequest: BatimentUpdatedRequest = BatimentUpdatedRequest(oldBuilding, newBuilding)
        return withContext(Dispatchers.IO) {
            try {
                val response = buildingService.updateBuilding(batimentUpdatedRequest)
                response.isSuccessful && response.body()?.message == "Batiment modifié avec sccès"
            } catch (e: Exception) {
                Log.e("BuildingRepository", "Erreur modification : ${e.message}")
                false
            }
        }
    }

    suspend fun deleteBuilding(nomC: String, codeB: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = buildingService.deleteBuilding(nomC, codeB)
                response.isSuccessful && response.body()?.message == "Batiment supprimé avec succès"
            } catch (e: Exception) {
                Log.e("BuildingRepository", "Erreur suppression : ${e.message}")
                false
            }
        }
    }

}
