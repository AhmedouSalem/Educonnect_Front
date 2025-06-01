package com.educonnect.repository

import com.educonnect.model.ResourceDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ResourceService {

    @GET("/api/resources/course/{courseId}")
    suspend fun getResourcesByCourse(@Path("courseId") courseId: Long): List<ResourceDto>

    @POST("/api/resources")
    suspend fun createResource(@Body resource: ResourceDto): ResourceDto

    @PUT("/api/resources/{id}")
    suspend fun updateResource(@Path("id") id: Long, @Body resource: ResourceDto): ResourceDto

    @DELETE("/api/resources/{id}")
    suspend fun deleteResource(@Path("id") id: Long?)
}