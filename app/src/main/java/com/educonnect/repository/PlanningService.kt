package com.educonnect.repository

import com.educonnect.model.PlanningDto
import com.educonnect.model.GenericResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PlanningService {
    @POST("api/planning")
    suspend fun addPlanning(@Body planningDto: PlanningDto): Response<GenericResponse>


    @GET("/api/planning/teachers/{id}")
    suspend fun getPlanningForTeacher(@Path("id") id: Long): List<com.educonnect.model.planning.PlanningDto>
}
