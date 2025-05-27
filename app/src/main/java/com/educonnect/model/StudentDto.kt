package com.educonnect.model

data class StudentDto(
    val id: Long? = null,
    val nom: String,
    val prenom: String,
    val email: String,
    val password: String,
    val codeINE: String, // correspond à codeINE
    val role: String = "ETUDIANT",
    val parcours: String?,
    val niveau: String?, // spécifique Student
    val groupe: String?, // spécifique Student
    val modeActuel: String? = "NORMAL" // facultatif, par défaut
)
