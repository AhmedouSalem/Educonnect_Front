package com.educonnect.repository

import com.educonnect.model.*
import retrofit2.Response

class UserRepository(private val api: UserService) {

    // Création générique
    suspend fun addUser(user: UserCreationRequest): Response<Unit> {
        return when (user.role.uppercase()) {
            "ETUDIANT" -> api.createStudent(user.toStudent())
            "PROFESSEUR" -> api.createTeacher(user.toTeacher())
            else -> throw IllegalArgumentException("Rôle non supporté : ${user.role}")
        }
    }

    // Étudiants
    suspend fun getAllStudents(): List<StudentDto> {
        val response = api.getAllStudents()
        if (response.isSuccessful) return response.body() ?: emptyList()
        else throw Exception("Erreur lors de la récupération des étudiants")
    }

    suspend fun getStudentById(id: Long): StudentDto {
        val response = api.getStudentById(id)
        if (response.isSuccessful) return response.body()!!
        else throw Exception("Étudiant non trouvé")
    }

    suspend fun deleteStudent(id: Long): Boolean {
        val response = api.deleteStudent(id)
        return response.isSuccessful
    }

    // Enseignants
    suspend fun getAllTeachers(): List<TeacherDto> {
        val response = api.getAllTeachers()
        if (response.isSuccessful) return response.body() ?: emptyList()
        else throw Exception("Erreur lors de la récupération des enseignants")
    }

    suspend fun getTeacherById(id: Long): TeacherDto {
        val response = api.getTeacherById(id)
        if (response.isSuccessful) return response.body()!!
        else throw Exception("Enseignant non trouvé")
    }

    suspend fun deleteTeacher(id: Long): Boolean {
        val response = api.deleteTeacher(id)
        return response.isSuccessful
    }

    suspend fun searchTeacherByName(nom: String): TeacherDto {
        val response = api.searchTeacherByName(nom)
        if (response.isSuccessful) return response.body()!!
        else throw Exception("Aucun enseignant trouvé avec ce nom")
    }

    // Mentions & Parcours
    suspend fun getAllMentions(): List<MentionDto> {
        val response = api.getMentions()
        if (response.isSuccessful) return response.body() ?: emptyList()
        else throw Exception("Erreur lors de la récupération des mentions")
    }

    suspend fun getAllParcours(): List<ParcoursDto> {
        val response = api.getAllParcours()
        if (response.isSuccessful) return response.body() ?: emptyList()
        else throw Exception("Erreur lors de la récupération des parcours")
    }

    // Conversion
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
        modeActuel = "NORMAL",
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

    fun toStudentWithId(req: UserCreationRequest, id: Long): StudentDto {
        return req.copy(password = "") // facultatif si tu ne veux pas modifier le mot de passe
            .let {
                StudentDto(
                    id = id,
                    nom = it.nom,
                    prenom = it.prenom,
                    email = it.email,
                    password = it.password,
                    codeINE = it.codeINE,
                    parcours = it.parcours,
                    role = "ETUDIANT",
                    niveau = it.niveau,
                    groupe = null,
                    modeActuel = "NORMAL"
                )
            }
    }

    fun toTeacherWithId(req: UserCreationRequest, id: Long): TeacherDto {
        return req.copy(password = "").let {
            TeacherDto(
                id = id,
                nom = it.nom,
                prenom = it.prenom,
                email = it.email,
                password = it.password,
                codeINE = it.codeINE,
                parcours = it.parcours,
                role = "PROFESSEUR",
                specialite = it.specialite,
                matieres = it.matieres ?: emptyList()
            )
        }
    }

    suspend fun updateStudent(id: Long?, request: UserCreationRequest): Boolean {
        requireNotNull(id) { "ID de l'étudiant ne peut pas être null" }
        val dto = toStudentWithId(request, id)
        val response = api.updateStudent(id, dto)
        return response.isSuccessful
    }

    suspend fun updateTeacher(id: Long?, request: UserCreationRequest): Boolean {
        requireNotNull(id) { "ID du professeur ne peut pas être null" }
        val dto = toTeacherWithId(request, id)
        val response = api.updateTeacher(id, dto)
        return response.isSuccessful
    }






}
