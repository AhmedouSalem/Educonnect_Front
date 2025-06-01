package com.educonnect.model

import java.time.LocalDateTime

data class CourseDto(
    val id: Long? = null,
    val code: String,
    val intitule: String,
    val description: String,
    val teacherId: Long?,
    val parcours: List<String>

)
