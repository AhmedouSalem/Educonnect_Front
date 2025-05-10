package com.educonnect.di

import com.educonnect.domain.auth.LoginUseCase
import com.educonnect.repository.AuthRepository
import com.educonnect.ui.auth.AuthViewModel

object Injection {

    private val authRepository by lazy {
        AuthRepository()
    }

    private val loginUseCase by lazy {
        LoginUseCase(authRepository)
    }

    fun provideAuthViewModel(): AuthViewModel {
        return AuthViewModel(loginUseCase)
    }
}
