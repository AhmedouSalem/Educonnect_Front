package com.educonnect.repository

import com.educonnect.model.AuthenticationRequest
import com.educonnect.model.AuthenticationResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
interface AuthService {

    /**
     * Endpoint pour la connexion de l'utilisateur.
     * @param request Les informations de connexion.
     * @return Response<AuthenticationResponse>
     */
    @POST("authenticate")
    suspend fun login(@Body request: AuthenticationRequest): Response<AuthenticationResponse>
}
