package com.educonnect.domain.admin

import com.educonnect.repository.SalleRepository

class AddSalleUseCase(private val salleRepository: SalleRepository) {

    suspend operator fun invoke(
        numero: String,
        capacite: String,
        type: String,
        etage: String,
        batimentCode: String
    ): Boolean {
        return salleRepository.addSalle(numero, capacite, type, etage, batimentCode)
    }
}
