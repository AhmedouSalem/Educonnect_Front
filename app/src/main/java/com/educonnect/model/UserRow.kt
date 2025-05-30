package com.educonnect.model

data class UserRow(
    val id: Long? = null,
    val nom: String,
    val prenom: String,
    val ine: String,
    val parcours: String,
    val niveau: String? = null,
    val email: String,
    val dateCreation: String,
    val specialite: String? = null, // null pour les étudiants
    val matieres: List<String>? = emptyList(),
)

fun StudentDto.toUserRow() = UserRow(
    id = id ?: 0L,
    nom = nom,
    prenom = prenom,
    ine = codeINE,
    parcours = parcours ?: "-",
    email=email,
    niveau = niveau,
    dateCreation = dateCreation ?: "-", // si ce champ existe
    specialite = null
)

fun TeacherDto.toUserRow() = UserRow(
    id = id ?: 0L,
    nom = nom,
    prenom = prenom,
    ine = codeINE,
    parcours = parcours ?: "-",
    email=email,
    dateCreation = dateCreation ?: "-", // si ce champ existe
    specialite = specialite ?: "-",
    matieres=matieres ?: null,
)
