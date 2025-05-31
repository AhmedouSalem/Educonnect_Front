package com.educonnect.domain.etudiant


import com.educonnect.model.StudentDto
import com.educonnect.repository.UserRepository

class GetStudentUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: Long): StudentDto? {
        return try {
            userRepository.getStudentById(userId)
        } catch (e: Exception) {
            null
        }
    }
}
