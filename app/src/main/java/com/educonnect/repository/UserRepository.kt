package com.educonnect.repository

import com.educonnect.model.MentionDto
import com.educonnect.model.ParcoursDto
import com.educonnect.model.StudentDto
import com.educonnect.model.TeacherDto
import com.educonnect.model.UserCreationRequest
import retrofit2.Response


class UserRepository(private val api: UserService) {

    suspend fun addUser(user: UserCreationRequest): Response<Unit> {
        return when (user.role.uppercase()) {
            "ETUDIANT" -> api.createStudent(user.toStudent())
            "PROFESSEUR" -> api.createTeacher(user.toTeacher())
            else -> throw IllegalArgumentException("Rôle non supporté")
        }
    }

    private fun UserCreationRequest.toStudent() = StudentDto(
        nom = nom,
        prenom = prenom,
        email = email,
        password = password,
        codeINE = codeINE,
        parcours = parcours,
        role = "ETUDIANT",
        niveau = niveau,
        groupe = groupe,
        modeActuel = "NORMAL"
    )

    private fun UserCreationRequest.toTeacher() = TeacherDto(
        nom = nom,
        prenom = prenom,
        email = email,
        password = password,
        codeINE = codeINE,
        parcours = parcours,
        role = "PROFESSEUR",
        specialite = specialite,
        matieres = matieres ?: emptyList()
    )

    suspend fun getAllMentions(): List<MentionDto> {
        val response = api.getMentions() // Retrofit call
        if (response.isSuccessful) return response.body() ?: emptyList()
        else throw Exception("Erreur lors de la récupération des mentions")
    }

    suspend fun getAllParcours(): List<ParcoursDto> {
        val response = api.getAllParcours()
        if (response.isSuccessful) return response.body() ?: emptyList()
        else throw Exception("Erreur lors de la récupération des parcours")
    }
}
