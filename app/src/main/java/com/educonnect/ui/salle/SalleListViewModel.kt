package com.educonnect.ui.salle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educonnect.domain.admin.DeleteSalleUseCase
import com.educonnect.domain.admin.GetAllSallesUseCase
import com.educonnect.domain.admin.UpdateSalleUseCase
import com.educonnect.model.SalleDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SalleListViewModel(
    private val getAllSallesUseCase: GetAllSallesUseCase,
    private val updateSalleUseCase: UpdateSalleUseCase,
    private val deleteSalleUseCase: DeleteSalleUseCase
) : ViewModel() {

    private val _salles = MutableStateFlow<List<SalleDto>>(emptyList())
    val salles: StateFlow<List<SalleDto>> = _salles

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    fun loadSalles() {
        viewModelScope.launch {
            _salles.value = getAllSallesUseCase()
        }
    }

    fun updateSalle(ancienne: SalleDto, updated: SalleDto, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = updateSalleUseCase(ancienne, updated)
            if (success) loadSalles()
            onResult(success)
        }
    }

    fun deleteSalle(numero: String, campusNom: String, batimentCode: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = deleteSalleUseCase(numero, campusNom, batimentCode)
            if (success) loadSalles()
            onResult(success)
        }
    }

    fun clearMessage() {
        _message.value = null
    }
}
