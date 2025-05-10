package com.educonnect.utils

import android.content.Context
import android.content.SharedPreferences
import com.educonnect.model.AuthenticationResponse

class SessionManager(context: Context) {

    companion object {
        private const val PREFS_NAME = "educonnect_prefs"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_ROLE = "role"
        private const val KEY_TOKEN = "token"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    /**
     * Sauvegarde les informations utilisateur.
     */
    fun saveUserData(authResponse: AuthenticationResponse) {
        prefs.edit().apply {
            putLong(KEY_USER_ID, authResponse.userId)
            putString(KEY_ROLE, authResponse.role)
            putString(KEY_TOKEN, authResponse.token)
            apply()
        }
    }

    /**
     * Récupère les informations utilisateur.
     */
    fun getUserData(): AuthenticationResponse? {
        val userId = prefs.getLong(KEY_USER_ID, -1L)
        val role = prefs.getString(KEY_ROLE, null)
        val token = prefs.getString(KEY_TOKEN, null)

        return if (userId != -1L && role != null && token != null) {
            AuthenticationResponse(userId, role, token)
        } else {
            null
        }
    }

    /**
     * Supprime les informations utilisateur.
     */
    fun clearUserData() {
        prefs.edit().clear().apply()
    }
}
