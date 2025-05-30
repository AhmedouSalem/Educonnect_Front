package com.educonnect.repository

import com.educonnect.model.*
import retrofit2.Response
import retrofit2.http.*

interface UserService {

    // Étudiants
    @POST("/api/students")
    suspend fun createStudent(@Body student: StudentDto): Response<Unit>

    @GET("/api/students")
    suspend fun getAllStudents(): Response<List<StudentDto>>

    @GET("/api/students/{id}")
    suspend fun getStudentById(@Path("id") id: Long): Response<StudentDto>

    @PUT("/api/students/{id}")
    suspend fun updateStudent(@Path("id") id: Long?, @Body student: StudentDto): Response<Unit>

    @DELETE("/api/students/{id}")
    suspend fun deleteStudent(@Path("id") id: Long?): Response<Unit>

    // Enseignants
    @POST("/api/teachers")
    suspend fun createTeacher(@Body teacher: TeacherDto): Response<Unit>

    @GET("/api/teachers")
    suspend fun getAllTeachers(): Response<List<TeacherDto>>

    @GET("/api/teachers/{id}")
    suspend fun getTeacherById(@Path("id") id: Long): Response<TeacherDto>

    @PUT("/api/teachers/{id}")
    suspend fun updateTeacher(@Path("id") id: Long?, @Body teacher: TeacherDto): Response<Unit>

    @DELETE("/api/teachers/{id}")
    suspend fun deleteTeacher(@Path("id") id: Long?): Response<Unit>

    @GET("/api/teachers/search")
    suspend fun searchTeacherByName(@Query("nom") nom: String): Response<TeacherDto>

    // Mentions & Parcours
    @GET("/api/mention/mentions")
    suspend fun getMentions(): Response<List<MentionDto>>

    @GET("/api/parcours/parcours/all")
    suspend fun getAllParcours(): Response<List<ParcoursDto>>
}
