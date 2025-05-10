package com.educonnect.di

import android.content.Context
import com.educonnect.domain.auth.LoginUseCase
import com.educonnect.repository.AuthRepository
import com.educonnect.ui.auth.AuthViewModel
import com.educonnect.utils.SessionManager

object Injection {

    fun provideAuthRepository(context: Context): AuthRepository {
        val sessionManager = SessionManager(context)
        return AuthRepository(sessionManager)
    }

    fun provideLoginUseCase(context: Context): LoginUseCase {
        return LoginUseCase(provideAuthRepository(context))
    }

    fun provideAuthViewModel(context: Context): AuthViewModel {
        return AuthViewModel(provideLoginUseCase(context))
    }
}
