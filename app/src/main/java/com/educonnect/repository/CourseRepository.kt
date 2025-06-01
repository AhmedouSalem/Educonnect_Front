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

    suspend fun addCourse(course: CourseDto): CourseDto {
        val response = courseService.addCourse(course)
        if (response.isSuccessful) return response.body()!!
        else throw Exception("Erreur HTTP : ${response.code()}")
    }

    suspend fun updateCourse(id: Long, course: CourseDto): CourseDto {
        val response = courseService.updateCourse(id, course)
        if (response.isSuccessful) return response.body()!!
        else throw Exception("Erreur lors de la mise à jour du cours : ${response.code()}")
    }

    suspend fun deleteCourse(id: Long) {
        val response = courseService.deleteCourse(id)
        if (!response.isSuccessful) {
            throw Exception("Erreur lors de la suppression du cours : ${response.code()}")
        }
    }

    suspend fun getCourseById(id: Long): CourseDto {
        val response = courseService.getCourseById(id)
        if (response.isSuccessful) return response.body()!!
        else throw Exception("Erreur récupération cours par ID : ${response.code()}")
    }

}
