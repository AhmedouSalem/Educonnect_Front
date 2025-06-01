package com.educonnect.domain.admin

import com.educonnect.model.SalleDto
import com.educonnect.repository.SalleRepository

class UpdateSalleUseCase(private val repository: SalleRepository) {
    suspend operator fun invoke(ancienne: SalleDto, nouvelle: SalleDto): Boolean {
        return repository.updateSalle(ancienne, nouvelle)
    }
}
