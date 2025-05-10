package com.educonnect.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educonnect.domain.auth.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _loginStatus = MutableStateFlow<String?>(null)
    val loginStatus: StateFlow<String?> = _loginStatus

    private val _userRole = MutableStateFlow<String?>(null)
    val userRole: StateFlow<String?> = _userRole

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun authenticate() {
        viewModelScope.launch {
            val response = loginUseCase.execute(_email.value, _password.value)
            response.collect { result ->
                Log.d("AuthViewModel", "Result from loginUseCase: $result")
                when (result.lowercase()) {
                    "admin", "student", "teacher" -> {
                        Log.d("AuthViewModel", "User Role Set: $result")
                        _userRole.emit(result)  // Utiliser `emit` pour forcer l'émission
                    }
                    else -> {
                        _loginStatus.value = result
                    }
                }
            }
        }
    }
}

