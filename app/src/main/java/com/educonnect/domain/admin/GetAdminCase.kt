package com.educonnect.domain.admin

import com.educonnect.model.UserInfosResponseById
import com.educonnect.repository.AdminRepository

class GetAdminUseCase(private val adminRepository: AdminRepository) {

    suspend operator fun invoke(userId: Long): UserInfosResponseById? {
        return adminRepository.getAdmin(userId)
    }
}
