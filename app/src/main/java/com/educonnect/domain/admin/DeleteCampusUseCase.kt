package com.educonnect.domain.admin

import com.educonnect.repository.CampusRepository

class DeleteCampusUseCase(private val repository: CampusRepository) {
    suspend operator fun invoke(nom: String): Boolean {
        return repository.deleteCampus(nom)
    }
}
