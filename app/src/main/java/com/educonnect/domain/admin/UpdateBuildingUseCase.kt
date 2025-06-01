package com.educonnect.domain.admin

import com.educonnect.model.BatimentDto
import com.educonnect.repository.BuildingRepository

class UpdateBuildingUseCase(
    private val repository: BuildingRepository
) {
    suspend operator fun invoke(oldBatiment: BatimentDto, newBatiment: BatimentDto): Boolean {
        return repository.updateBuilding(oldBatiment, newBatiment)
    }
}
