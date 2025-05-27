package com.educonnect.domain.admin

import com.educonnect.repository.BuildingRepository

class AddBuildingUseCase(
    private val repository: BuildingRepository
) {
    suspend operator fun invoke(code: String, anneC: String, campusNom: String): Boolean {
        return repository.addBuilding(code, anneC, campusNom)
    }
}
