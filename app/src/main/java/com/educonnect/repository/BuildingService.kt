package com.educonnect.repository

import com.educonnect.model.BatimentDto
import com.educonnect.model.GenericResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BuildingService {

    @POST("api/admin/batiments")
    suspend fun addBuilding(@Body request: BatimentDto): Response<GenericResponse>

    @GET("api/admin/batiments")
    suspend fun getAllBatiments(): Response<List<BatimentDto>>

}
