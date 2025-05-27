package com.educonnect.repository

import android.util.Log
import com.educonnect.di.NetworkModule
import com.educonnect.model.CampusDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class CampusRepository {

    private val campusService = NetworkModule.campusService;

    suspend fun addCampus(nom: String, ville: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val request = CampusDto(nom, ville)
                val response = campusService.addCampus(request)
                when {
                    response.isSuccessful && response.body()?.message == "Campus ajouté avec succès !" -> {
                        Log.d("CampusRepository", "Campus ajouté avec succès")
                        true
                    }
                    response.code() == 409 -> {
                        Log.e("CampusRepository", "Campus déjà existant")
                        throw Exception("Ce campus existe déjà.")
                    }
                    else -> {
                        Log.e("CampusRepository", "Erreur API : ${response.code()} - ${response.body()?.message}")
                        false
                    }
                }
            } catch (e: HttpException) {
                Log.e("CampusRepository", "Erreur HTTP : ${e.message}")
                false
            } catch (e: IOException) {
                Log.e("CampusRepository", "Erreur Réseau : ${e.message}")
                false
            }
        }
    }


    suspend fun getAllCampus(): List<String> {
        return withContext(Dispatchers.IO) {
            try {
                val response = NetworkModule.campusService.getAllCampus()
                if (response.isSuccessful) {
                    response.body()?.map { it.nom } ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

}
