package com.educonnect.model

data class UserCreationRequest(
    val nom: String,
    val prenom: String,
    val email: String,
    val password: String,
    val codeINE: String,
    val role: String,
    val parcours: String?,
    val niveau: String? = null,
    val groupe: String? = null,
    val specialite: String? = null,
    val matieres: List<String>? = null
)

