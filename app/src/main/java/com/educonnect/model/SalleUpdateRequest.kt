package com.educonnect.model

data class SalleUpdateRequest(
    val ancienne: SalleDto,
    val nouvelle: SalleDto
)
