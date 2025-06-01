package com.educonnect.model.planning

data class PlanningDto(
    val id: Long,
    val titre: String,
    val typeCours: String,
    val heureDebut: List<Int>,
    val heureFin: List<Int>,
    val date: List<Int>,
    val course: CourseDto,
    val salle: SalleDto
)

data class CourseDto(
    val id: Long,
    val code: String,
    val intitule: String,
    val description: String,
    val listparcours: List<ParcoursDto>,
    val enseignant: EnseignantDto
)

data class SalleDto(
    val id: Long,
    val nums: String,
    val typeS: String,
    val etage: String,
    val batiment: BatimentDto
)

data class BatimentDto(
    val codeB: String,
    val campus: CampusDto
)

data class CampusDto(
    val nomC: String,
    val ville: String
)

data class EnseignantDto(
    val id: Long,
    val nom: String,
    val prenom: String,
    val email: String
)

data class ParcoursDto(
    val code: String,
    val name: String
)
