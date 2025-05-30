package com.educonnect.domain.admin

import com.educonnect.model.PlanningDto
import com.educonnect.repository.PlanningRepository

class AddPlanningUseCase(private val planningRepository: PlanningRepository) {

    suspend operator fun invoke(planningDto: PlanningDto): Boolean {
        return planningRepository.addPlanning(planningDto)
    }
}
