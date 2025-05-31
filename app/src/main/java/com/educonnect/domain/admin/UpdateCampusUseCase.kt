package com.educonnect.domain.admin

import com.educonnect.model.CampusDto
import com.educonnect.repository.CampusRepository

class UpdateCampusUseCase(private val repository: CampusRepository) {
    suspend operator fun invoke(oldNom: String, updatedCampus: CampusDto): Boolean {
        return repository.updateCampus(oldNom, updatedCampus)
    }
}
