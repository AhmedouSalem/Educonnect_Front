package com.educonnect.repository

import android.util.Log
import com.educonnect.di.NetworkModule
import com.educonnect.model.MentionDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class MentionRepository {
    private val userService = NetworkModule.userApi

    private val mentionService = NetworkModule.mentionService

    fun provideMentionRepository(): MentionRepository {
        return MentionRepository()
    }



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

    suspend fun getListMentions(): List<MentionDto> = mentionService.getAllMentions()

    suspend fun getMentionById(id: Long): MentionDto = mentionService.getMentionById(id)

    suspend fun createMention(dto: MentionDto): MentionDto = mentionService.createMention(dto)

    suspend fun updateMention(id: Long, dto: MentionDto): MentionDto = mentionService.updateMention(id, dto)

    suspend fun deleteMention(id: Long) {
        val response = mentionService.deleteMention(id)
        if (!response.isSuccessful) {
            throw Exception("Erreur suppression: code ${response.code()}")
        }
    }

    suspend fun getMentionByIntitule(intitule: String): MentionDto {
        return mentionService.getMentionByIntitule(intitule)
    }


}
