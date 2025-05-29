package com.educonnect.repository

import com.educonnect.model.MentionDto
import retrofit2.Response
import retrofit2.http.GET

interface MentionService {

    @GET("api/mention/mentions")
    suspend fun getAllMentions(): Response<List<MentionDto>>
}
