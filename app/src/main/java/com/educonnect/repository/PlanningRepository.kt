package com.educonnect.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.educonnect.di.NetworkModule
import com.educonnect.domain.teacher.GetTeacherUseCase
import com.educonnect.domain.teacher.toCoursUiModel
import com.educonnect.model.CoursUiModel
import com.educonnect.model.PlanningDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class PlanningRepository {

    private val planningService = NetworkModule.planningService

    suspend fun addPlanning(planningDto: PlanningDto): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = planningService.addPlanning(planningDto)

                when {

                    response.isSuccessful && response.body()?.message == "Planning ajouté avec succès" -> {
                        Log.d("PlanningRepository", "Planning ajouté")
                        true
                    }
                    response.code() == 404 -> {
                        throw Exception(response.body()?.message ?: "Cours ou salle introuvable")
                    }
                    response.code() == 400 -> {
                        throw Exception(response.body()?.message ?: "Heure invalide")
                    }
                    response.code() == 409 -> {
                        throw Exception(response.body()?.message ?: "Conflit de réservation")
                    }
                    else -> {
                        Log.e("PlanningRepository", "Erreur API : ${response.code()} - ${response.body()?.message}")
                        false
                    }
                }

            } catch (e: HttpException) {
                Log.e("PlanningRepository", "Erreur HTTP : ${e.message}")
                false
            } catch (e: IOException) {
                Log.e("PlanningRepository", "Erreur Réseau : ${e.message}")
                false
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getPlanningForTeacher(id: Long): List<CoursUiModel> {
        return planningService.getPlanningForTeacher(id).map {  it.toCoursUiModel() }
    }
}

