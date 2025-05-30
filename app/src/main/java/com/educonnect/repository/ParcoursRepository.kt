package com.educonnect.repository

import android.util.Log
import com.educonnect.di.NetworkModule
import com.educonnect.model.ParcoursDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class ParcoursRepository {

    private val parcoursService = NetworkModule.parcoursService

    suspend fun getParcoursByMention(mentionName: String): List<ParcoursDto> {
        return withContext(Dispatchers.IO) {
            try {
                val response = parcoursService.getParcoursByMention(mentionName)
                if (response.isSuccessful) {
                    response.body() ?: emptyList()
                } else {
                    Log.e("ParcoursRepository", "Erreur API : ${response.code()}")
                    emptyList()
                }
            } catch (e: IOException) {
                Log.e("ParcoursRepository", "Erreur Réseau : ${e.message}")
                emptyList()
            }
        }
    }

}
