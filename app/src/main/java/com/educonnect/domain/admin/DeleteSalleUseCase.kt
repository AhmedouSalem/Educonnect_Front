package com.educonnect.domain.admin

import com.educonnect.repository.SalleRepository

class DeleteSalleUseCase(private val repository: SalleRepository) {
    suspend operator fun invoke(numero: String, campusNom: String, batimentCode: String): Boolean {
        return repository.deleteSalle(numero, campusNom, batimentCode)
    }
}
