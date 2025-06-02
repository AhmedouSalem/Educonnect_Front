package com.educonnect.ui.home.teacher.planning

import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educonnect.di.AppSession
import com.educonnect.model.CoursUiModel
import com.educonnect.repository.PlanningRepository
import kotlinx.coroutines.launch
import android.util.Log
import androidx.annotation.RequiresApi

class TeacherPlanningViewModel(
    private val planningRepository: PlanningRepository
) : ViewModel() {
    val sessionManager = AppSession.sessionManager
    val userId: Long = sessionManager.getUserData()?.userId!!

    init {
        Log.d("HomeViewModel", "User ID: $userId")
    }

    var coursList by mutableStateOf<List<CoursUiModel>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadPlanningForTeacher(id: Long) {
        viewModelScope.launch {
            isLoading = true
            try {
                coursList = planningRepository.getPlanningForTeacher(id)
            } catch (e: Exception) {
                // Log error ou afficher un message
                coursList = emptyList()
            } finally {
                isLoading = false
            }
        }
    }
}