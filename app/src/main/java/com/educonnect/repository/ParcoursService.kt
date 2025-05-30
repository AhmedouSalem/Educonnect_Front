package com.educonnect.repository

import com.educonnect.model.ParcoursDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ParcoursService {

    @GET("api/parcours/parcours/mention/{mentionName}")
    suspend fun getParcoursByMention(@Path("mentionName") mentionName: String): Response<List<ParcoursDto>>

    @GET("/api/parcours/parcours/all")
    suspend fun getAllParcours(): List<ParcoursDto>

    @GET("/api/parcours/parcours/{code}")
    suspend fun getParcoursByCode(@Path("code") code: String): ParcoursDto

    @POST("/api/parcours/parcours")
    suspend fun createParcours(@Body dto: ParcoursDto): ParcoursDto

    @PUT("/api/parcours/parcours/{code}")
    suspend fun updateParcours(@Path("code") code: String, @Body dto: ParcoursDto): ParcoursDto

    @DELETE("/api/parcours/parcours/{code}")
    suspend fun deleteParcours(@Path("code") code: String): Response<Unit>
}
