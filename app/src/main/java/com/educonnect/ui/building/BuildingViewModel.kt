package com.educonnect.ui.building

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educonnect.domain.admin.AddBuildingUseCase
import com.educonnect.repository.CampusRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BuildingViewModel(
    private val addBuildingUseCase: AddBuildingUseCase,
    private val campusRepository: CampusRepository
) : ViewModel() {

    private val _code = MutableStateFlow("")
    val code: StateFlow<String> = _code

    private val _anneC = MutableStateFlow("")
    val anneC: StateFlow<String> = _anneC

    private val _campusNom = MutableStateFlow("")
    val campusNom: StateFlow<String> = _campusNom

    private val _campusList = MutableStateFlow<List<String>>(emptyList())
    val campusList: StateFlow<List<String>> = _campusList

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    init {
        chargerCampus()
    }

    fun onCodeChange(newValue: String) {
        _code.value = newValue
    }

    fun onAnneCChange(newValue: String) {
        _anneC.value = newValue
    }

    fun onCampusNomChange(newValue: String?) {
        _campusNom.value = newValue ?: ""
    }

    fun clearMessage() {
        _message.value = null
    }

    fun ajouterBatiment() {
        viewModelScope.launch {
            if (_code.value.isBlank() || _anneC.value.isBlank() || _campusNom.value.isBlank()) {
                _message.value = "Tous les champs sont obligatoires"
                return@launch
            }

            try {
                val result = addBuildingUseCase(
                    code = _code.value.trim(),
                    anneC = _anneC.value.trim(),
                    campusNom = _campusNom.value.trim()
                )

                _message.value = if (result) {
                    _code.value = ""
                    _anneC.value = ""
                    _campusNom.value = ""
                    "Bâtiment ajouté avec succès"
                } else {
                    "Erreur inconnue"
                }

            } catch (e: Exception) {
                _message.value = e.message ?: "Erreur inattendue"
            }
        }
    }

    private var alreadyLoaded = false

    private fun chargerCampus() {
        if (alreadyLoaded) return
        alreadyLoaded = true

        viewModelScope.launch {
            val result = campusRepository.getAllCampus()
                .distinctBy { it.lowercase().trim() }

            _campusList.value = result
        }
    }
}
