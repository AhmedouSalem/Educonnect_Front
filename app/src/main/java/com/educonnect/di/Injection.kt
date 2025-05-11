package com.educonnect.di

import android.content.Context
import com.educonnect.domain.admin.GetAdminUseCase
import com.educonnect.domain.auth.LoginUseCase
import com.educonnect.repository.AdminRepository
import com.educonnect.repository.AuthRepository
import com.educonnect.ui.auth.AuthViewModel
import com.educonnect.ui.home.HomeViewModel
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

    fun provideAdminRepository(context: Context): AdminRepository {
        val sessionManager = SessionManager(context)
        return AdminRepository(sessionManager)
    }

    fun provideGetAdminUseCase(context: Context): GetAdminUseCase {
        return GetAdminUseCase(provideAdminRepository(context))
    }

    fun provideHomeViewModel(context: Context): HomeViewModel {
        val sessionManager = SessionManager(context)
        return HomeViewModel(provideGetAdminUseCase(context), sessionManager)
    }
}
