package com.educonnect.repository

import com.educonnect.model.SalleDto
import com.educonnect.model.GenericResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SalleService {

    @POST("api/admin/salles")
    suspend fun addSalle(@Body salleDto: SalleDto): Response<GenericResponse>
}
