package com.educonnect.repository

import com.educonnect.model.CampusDto
import com.educonnect.model.GenericResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface CampusService {
    @POST("api/admin/campus")
    suspend fun addCampus(@Body campus: CampusDto): Response<GenericResponse>
    @GET("api/admin/campus")
    suspend fun getAllCampus(): Response<List<CampusDto>>

    @PUT("api/admin/campus/{campusName}")
    suspend fun updateCampus(
        @Path("campusName") campusName: String,
        @Body campusDto: CampusDto
    ): Response<GenericResponse>

    @DELETE("api/admin/campus/{campusName}")
    suspend fun deleteCampus(@Path("campusName") campusName: String): Response<GenericResponse>
}
