package com.educonnect.domain.admin

import com.educonnect.repository.CampusRepository

class AddCampusUseCase(private val campusRepository: CampusRepository) {

    suspend operator fun invoke(nom: String, ville: String): Boolean {
        return campusRepository.addCampus(nom, ville)
    }
}
