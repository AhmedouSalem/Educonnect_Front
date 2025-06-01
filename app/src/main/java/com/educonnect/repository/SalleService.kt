package com.educonnect.repository

import com.educonnect.model.SalleDto
import com.educonnect.model.GenericResponse
import com.educonnect.model.SalleUpdateRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface SalleService {

    @POST("api/admin/salles")
    suspend fun addSalle(@Body salleDto: SalleDto): Response<GenericResponse>

    @GET("api/admin/salles/{nomC}/{batiment_code}")
    suspend fun getSallesByBatimentAndCampus(@Path("nomC") nomC: String, @Path("batiment_code") batiment_code: String): Response<List<SalleDto>>

    @GET("api/admin/salles")
    suspend fun getAllSalles(): Response<List<SalleDto>>

    @PUT("api/admin/salles")
    suspend fun updateSalle(
        @Body request: SalleUpdateRequest
    ): Response<GenericResponse>


    @DELETE("api/admin/salles/{numero}/{campusNom}/{batimentCode}")
    suspend fun deleteSalle(
        @Path("numero") numero: String,
        @Path("campusNom") campusNom: String,
        @Path("batimentCode") batimentCode: String
    ): Response<GenericResponse>

}
