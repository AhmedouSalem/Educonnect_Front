package com.educonnect.ui.home.etudiant

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educonnect.domain.etudiant.GetStudentUseCase
import com.educonnect.model.StudentDto
import com.educonnect.model.TeacherDto
import com.educonnect.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StudentHomeViewModel(
    private val getStudentUseCase: GetStudentUseCase,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _studentData = MutableStateFlow<StudentDto?>(null)
    val studentData: StateFlow<StudentDto?> = _studentData

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchStudentData()
    }

    private fun fetchStudentData() {
        viewModelScope.launch {
            _isLoading.value = true
            val userId = sessionManager.getUserData()?.userId
            Log.d("StudentHomeViewModel", "User ID: $userId")
            userId?.let {
                val student = getStudentUseCase(it)
                _studentData.value = student
            }
            _isLoading.value = false
        }
    }

    fun clearSession() {
        sessionManager.clearUserData()
        _studentData.value = null
    }
}
