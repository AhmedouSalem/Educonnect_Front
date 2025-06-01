package com.educonnect.repository

import com.educonnect.model.BatimentDto
import com.educonnect.model.BatimentUpdatedRequest
import com.educonnect.model.GenericResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BuildingService {

    @POST("api/admin/batiments")
    suspend fun addBuilding(@Body request: BatimentDto): Response<GenericResponse>

    @GET("api/admin/batiments")
    suspend fun getAllBatiments(): Response<List<BatimentDto>>

    @GET("api/admin/batiments/{nomC}")
    suspend fun getBatimentsByCampus(@Path("nomC") campusName: String): Response<List<BatimentDto>>

    @PUT("api/admin/batiments")
    suspend fun updateBuilding(
        @Body batimentUpdatedRequest: BatimentUpdatedRequest
    ): Response<GenericResponse>

    @DELETE("api/admin/batiments/{nomC}/{codeB}")
    suspend fun deleteBuilding(@Path("nomC") nomC: String, @Path("codeB") codeB: String): Response<GenericResponse>


}
