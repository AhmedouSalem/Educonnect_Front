package com.educonnect.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educonnect.domain.admin.GetAdminUseCase
import com.educonnect.model.UserInfosResponseById
import com.educonnect.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAdminUseCase: GetAdminUseCase,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _adminData = MutableStateFlow<UserInfosResponseById?>(null)
    val adminData: StateFlow<UserInfosResponseById?> = _adminData

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchAdminData()
    }

    private fun fetchAdminData() {
        viewModelScope.launch {
            _isLoading.value = true

            val userId = sessionManager.getUserData()?.userId
            Log.d("HomeViewModel", "User ID: $userId")

            userId?.let {
                val admin = getAdminUseCase(it)
                if (admin != null) {
                    _adminData.value = admin
                } else {
                    Log.e("HomeViewModel", "Admin data is null")
                }
            }

            _isLoading.value = false
        }
    }

    fun clearSession() {
        sessionManager.clearUserData()
        _adminData.value = null
        Log.d("HomeViewModel", "Session cleared and adminData set to null")
    }

}

