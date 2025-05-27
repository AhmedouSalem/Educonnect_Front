package com.educonnect.ui.salle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educonnect.domain.admin.AddSalleUseCase
import com.educonnect.repository.BuildingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SalleViewModel(
    private val addSalleUseCase: AddSalleUseCase,
    private val buildingRepository: BuildingRepository,
) : ViewModel() {

    private val _numero = MutableStateFlow("")
    val numero: StateFlow<String> = _numero

    private val _capacite = MutableStateFlow("")
    val capacite: StateFlow<String> = _capacite

    private val _type = MutableStateFlow("")
    val type: StateFlow<String> = _type

    private val _etage = MutableStateFlow("")
    val etage: StateFlow<String> = _etage

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    // Listes statiques
    val typesDeSalles = listOf("Amphi", "SC", "TD", "TP", "Numérique")
    val etages = listOf("0", "1", "2", "3", "4", "5")

    private val _batimentCode = MutableStateFlow("")
    val batimentCode: StateFlow<String> = _batimentCode

    private val _batimentList = MutableStateFlow<List<String>>(emptyList())
    val batimentList: StateFlow<List<String>> = _batimentList

    init {
        chargerBatiments()
    }

    fun onBatimentNomChange(newValue: String) {
        _batimentCode.value = newValue
    }


    fun onNumeroChange(newValue: String) {
        _numero.value = newValue
    }

    fun onCapaciteChange(newValue: String) {
        _capacite.value = newValue
    }

    fun onTypeChange(newValue: String) {
        _type.value = newValue
    }

    fun onEtageChange(newValue: String) {
        _etage.value = newValue
    }

    fun clearMessage() {
        _message.value = null
    }

    private fun chargerBatiments() {
        viewModelScope.launch {
            val result = buildingRepository.getAllBatimentCodes()
            _batimentList.value = result.distinct()
        }
    }


    fun ajouterSalle() {
        viewModelScope.launch {
            if (_numero.value.isBlank() || _capacite.value.isBlank() || _type.value.isBlank() || _etage.value.isBlank()) {
                _message.value = "Tous les champs sont obligatoires"
                return@launch
            }

            try {
                val result = addSalleUseCase(
                    numero = _numero.value.trim(),
                    capacite = _capacite.value.trim(),
                    type = _type.value.trim(),
                    etage = _etage.value.trim(),
                    batimentCode = _batimentCode.value.trim()
                )

                _message.value = if (result) {
                    _numero.value = ""
                    _capacite.value = ""
                    _type.value = ""
                    _etage.value = ""
                    "Salle ajoutée avec succès"
                } else {
                    "Erreur inconnue"
                }

            } catch (e: Exception) {
                _message.value = e.message ?: "Erreur inattendue"
            }
        }
    }
}