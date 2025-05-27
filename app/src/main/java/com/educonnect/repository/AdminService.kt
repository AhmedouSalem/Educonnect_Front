package com.educonnect.repository

import com.educonnect.model.UserInfosResponseById
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AdminService {

    /**
     * Requête GET pour récupérer les informations d'un admin.
     * @param id L'identifiant de l'admin.
     * @return Response<Admin>
     */
    @GET("api/admin/admins/{id}")
    suspend fun getAdmin(@Path("id") id: Long): Response<UserInfosResponseById>
}
