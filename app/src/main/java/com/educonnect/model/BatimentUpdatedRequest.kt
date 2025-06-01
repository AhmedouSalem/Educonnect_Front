package com.educonnect.model

data class BatimentUpdatedRequest(
    val ancien: BatimentDto,
    val nouveau: BatimentDto
)