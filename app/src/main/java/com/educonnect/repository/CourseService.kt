package com.educonnect.repository

import com.educonnect.model.CourseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CourseService {

    @GET("api/cours/cours/{parcoursCode}")
    suspend fun getCoursesByParcours(@Path("parcoursCode") parcoursCode: String): Response<List<CourseDto>>

    @GET("/api/courses/course")
    suspend fun getCourses(): Response<List<CourseDto>>

    @POST("/api/courses/course")
    suspend fun addCourse(@Body course: CourseDto): Response<CourseDto>

    @PUT("/api/courses/course/{id}")
    suspend fun updateCourse(@Path("id") id: Long, @Body course: CourseDto): Response<CourseDto>

    @DELETE("/api/courses/course/{id}")
    suspend fun deleteCourse(@Path("id") id: Long): Response<Void>

    @GET("/api/courses/course/{id}")
    suspend fun getCourseById(@Path("id") id: Long): Response<CourseDto>

}
