package com.educonnect.model

data class PlanningDto(
    val mention: String,
    val parcours: String,
    val cours: String,
    val typeCours: String,
    val nomCampus: String,
    val batiment: String,
    val salle: String,
    val heureDebut: String,
    val heureFin: String,
    val date: String
)
