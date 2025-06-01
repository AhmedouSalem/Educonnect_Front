package com.educonnect.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educonnect.domain.teacher.GetTeacherUseCase
import com.educonnect.model.TeacherDto
import com.educonnect.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TeacherHomeViewModel(
    private val getTeacherUseCase: GetTeacherUseCase,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _teacherData = MutableStateFlow<TeacherDto?>(null)
    val teacherData: StateFlow<TeacherDto?> = _teacherData

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchTeacherData()
    }

    private fun fetchTeacherData() {
        viewModelScope.launch {
            _isLoading.value = true
            val userId = sessionManager.getUserData()?.userId
            Log.d("TeacherHomeViewModel", "User ID: $userId")
            userId?.let {
                val teacher = getTeacherUseCase(it)
                _teacherData.value = teacher
            }
            _isLoading.value = false
        }
    }

    fun clearSession() {
        sessionManager.clearUserData()
        _teacherData.value = null
    }
}