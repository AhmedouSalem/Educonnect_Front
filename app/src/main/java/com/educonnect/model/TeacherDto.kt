package com.educonnect.model

data class TeacherDto(
    val id: Long? = null,
    val nom: String,
    val prenom: String,
    val email: String,
    val password: String,
    val codeINE: String, // correspond à codeINE
    val role: String = "PROFESSEUR",
    val parcours: String?,
    val specialite: String?,         // spécifique Teacher
    val matieres: List<String>? = emptyList() // facultatif
)
