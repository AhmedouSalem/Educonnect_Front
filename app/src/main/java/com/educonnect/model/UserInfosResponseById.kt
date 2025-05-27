package com.educonnect.model

data class UserInfosResponseById(
    val id: Long,
    val nom: String,
    val prenom: String,
    val email: String,
    val privileges: List<String> = emptyList()
)
