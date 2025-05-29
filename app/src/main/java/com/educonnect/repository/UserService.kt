package com.educonnect.repository


import com.educonnect.model.MentionDto
import com.educonnect.model.ParcoursDto
import com.educonnect.model.StudentDto
import com.educonnect.model.TeacherDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @POST("/api/students")
    suspend fun createStudent(@Body student: StudentDto): Response<Unit>

    @POST("/api/teachers")
    suspend fun createTeacher(@Body teacher: TeacherDto): Response<Unit>


    @GET("/api/mention/mentions")
    suspend fun getMentions(): Response<List<MentionDto>>

    @GET("/api/parcours/parcours/all")
    suspend fun getAllParcours(): Response<List<ParcoursDto>>




}
