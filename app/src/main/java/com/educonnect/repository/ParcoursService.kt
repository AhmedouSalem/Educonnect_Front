package com.educonnect.repository

import com.educonnect.model.ParcoursDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ParcoursService {

    @GET("api/parcours/parcours/mention/{mentionName}")
    suspend fun getParcoursByMention(@Path("mentionName") mentionName: String): Response<List<ParcoursDto>>
}
