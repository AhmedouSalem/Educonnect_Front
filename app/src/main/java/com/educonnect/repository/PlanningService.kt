package com.educonnect.repository

import com.educonnect.model.PlanningDto
import com.educonnect.model.GenericResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PlanningService {
    @POST("api/planning")
    suspend fun addPlanning(@Body planningDto: PlanningDto): Response<GenericResponse>
}
