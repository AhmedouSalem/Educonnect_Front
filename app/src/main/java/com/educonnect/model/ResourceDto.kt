package com.educonnect.model

data class ResourceDto(
    val id: Long? = null,
    val nom: String,
    val type: String,
    val categorie: String,
    val url: String,
    val taille: Long,
    val courseId: Long
)
