package com.educonnect.domain.admin

import com.educonnect.model.SalleDto
import com.educonnect.repository.SalleRepository

class GetAllSallesUseCase(private val repository: SalleRepository) {
    suspend operator fun invoke(): List<SalleDto> {
        return repository.getAllSalles()
    }
}
