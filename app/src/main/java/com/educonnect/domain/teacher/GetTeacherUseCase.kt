package com.educonnect.domain.teacher

import com.educonnect.model.TeacherDto
import com.educonnect.repository.UserRepository

class GetTeacherUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: Long): TeacherDto? {
        return try {
            userRepository.getTeacherById(userId)
        } catch (e: Exception) {
            null
        }
    }
}
