package com.educonnect.repository


import com.educonnect.model.MentionDto
import retrofit2.Response
import retrofit2.http.*

interface MentionService {
    @GET("/api/mention/mentions")
    suspend fun getAllMentions(): List<MentionDto>

    @GET("/api/mention/mentions/{id}")
    suspend fun getMentionById(@Path("id") id: Long): MentionDto

    @POST("/api/mention/mentions")
    suspend fun createMention(@Body mentionDTO: MentionDto): MentionDto

    @PUT("/api/mention/mentions/{id}")
    suspend fun updateMention(@Path("id") id: Long, @Body mentionDTO: MentionDto): MentionDto

    @DELETE("/api/mention/mentions/{id}")
    suspend fun deleteMention(@Path("id") id: Long): Response<Unit>

    @GET("/api/mention/mentions/intitule/{intitule}")
    suspend fun getMentionByIntitule(@Path("intitule") intitule: String): MentionDto

}
