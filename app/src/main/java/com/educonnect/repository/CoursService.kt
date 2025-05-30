package com.educonnect.repository

import com.educonnect.model.CourseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CourseService {

    @GET("api/cours/cours/{parcoursCode}")
    suspend fun getCoursesByParcours(@Path("parcoursCode") parcoursCode: String): Response<List<CourseDto>>
}
