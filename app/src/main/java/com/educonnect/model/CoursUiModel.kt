package com.educonnect.model

import java.time.LocalDate

data class CoursUiModel(
    val heureDebut: String,
    val heureFin: String,
    val titre: String,
    val salle: String,
    val professeur: String,
    val groupes: List<String>,
    val description: String,
    val isHighlighted: Boolean = false,
    val dayOfMonth: Int,
    val date: LocalDate
)