package com.educonnect.domain.admin

import com.educonnect.repository.BuildingRepository

class DeleteBuildingUseCase(
    private val repository: BuildingRepository
) {
    suspend operator fun invoke(nomC: String, codeB: String): Boolean {
        return repository.deleteBuilding(nomC, codeB)
    }
}
