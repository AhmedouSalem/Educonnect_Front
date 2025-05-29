package com.educonnect.repository

import com.educonnect.model.SalleDto
import com.educonnect.model.GenericResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SalleService {

    @POST("api/admin/salles")
    suspend fun addSalle(@Body salleDto: SalleDto): Response<GenericResponse>

    @GET("api/admin/salles/{batimentCode}")
    suspend fun getSallesByBatiment(@Path("batimentCode") batimentCode: String): Response<List<SalleDto>>
}
