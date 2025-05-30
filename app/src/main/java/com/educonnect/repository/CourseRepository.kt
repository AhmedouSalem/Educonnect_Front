package com.educonnect.repository

import android.util.Log
import com.educonnect.di.NetworkModule
import com.educonnect.model.CourseDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class CourseRepository {

    private val courseService = NetworkModule.courseService

    suspend fun getCoursesByParcours(parcoursCode: String): List<CourseDto> {
        return withContext(Dispatchers.IO) {
            try {
                val response = courseService.getCoursesByParcours(parcoursCode)
                if (response.isSuccessful) {
                    response.body() ?: emptyList()
                } else {
                    Log.e("CourseRepository", "Erreur API : ${response.code()}")
                    emptyList()
                }
            } catch (e: IOException) {
                Log.e("CourseRepository", "Erreur Réseau : ${e.message}")
                emptyList()
            }
        }
    }

}
