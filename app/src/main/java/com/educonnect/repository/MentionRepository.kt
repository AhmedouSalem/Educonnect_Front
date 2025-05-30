package com.educonnect.repository

import android.util.Log
import com.educonnect.di.NetworkModule
import com.educonnect.model.MentionDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class MentionRepository {
    private val userService = NetworkModule.userApi


    suspend fun getAllMentions(): List<String> {
        return withContext(Dispatchers.IO) {
            try {
                val response = userService.getMentions()
                if (response.isSuccessful) {
                    response.body()?.mapNotNull { it.intitule } ?: emptyList()
                } else {
                    Log.e("MentionRepository", "Erreur API : ${response.code()}")
                    emptyList()
                }
            } catch (e: IOException) {
                Log.e("MentionRepository", "Erreur Réseau : ${e.message}")
                emptyList()
            }
        }
    }

}
