package com.educonnect.repository

import android.util.Log
import com.educonnect.di.NetworkModule
import com.educonnect.model.UserInfosResponseById
import com.educonnect.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class AdminRepository(private val sessionManager: SessionManager) {

    private val adminService = NetworkModule.adminService

    suspend fun getAdmin(userId: Long): UserInfosResponseById? {
        return withContext(Dispatchers.IO) {
            try {
                val response = adminService.getAdmin(userId)
                if (response.isSuccessful) {
                    Log.d("AdminRepository", "Admin reçu : ${response.body()}")
                    response.body()
                } else {
                    Log.e("AdminRepository", "Erreur : ${response.code()} - ${response.message()}")
                    null
                }
            } catch (e: HttpException) {
                Log.e("AdminRepository", "Erreur HTTP : ${e.message}")
                null
            } catch (e: IOException) {
                Log.e("AdminRepository", "Erreur Réseau : ${e.message}")
                null
            }
        }
    }
}
