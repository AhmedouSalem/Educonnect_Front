package com.educonnect.repository

import com.educonnect.model.CampusDto
import com.educonnect.model.GenericResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface CampusService {
    @POST("api/admin/campus")
    suspend fun addCampus(@Body campus: CampusDto): Response<GenericResponse>
    @GET("api/admin/campus")
    suspend fun getAllCampus(): Response<List<CampusDto>>

}
