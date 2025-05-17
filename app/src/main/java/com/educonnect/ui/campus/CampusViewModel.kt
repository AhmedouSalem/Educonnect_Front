package com.educonnect.ui.campus

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educonnect.domain.admin.AddCampusUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CampusViewModel(
    private val addCampusUseCase: AddCampusUseCase
) : ViewModel() {

    private val _nom = MutableStateFlow("")
    val nom: StateFlow<String> = _nom

    private val _ville = MutableStateFlow("")
    val ville: StateFlow<String> = _ville

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    fun onNomChange(newValue: String) {
        _nom.value = newValue
    }

    fun onVilleChange(newValue: String) {
        _ville.value = newValue
    }

    fun ajouterCampus() {
        viewModelScope.launch {
            if (_nom.value.isBlank() || _ville.value.isBlank()) {
                _message.value = "Tous les champs sont obligatoires"
                return@launch
            }

            try {
                val success = addCampusUseCase(nom.value, ville.value)
                _message.value = if (success) {
                    _nom.value = ""
                    _ville.value = ""
                    "Campus ajouté avec succès"
                } else {
                    "Erreur inconnue lors de l'ajout"
                }
            } catch (e: Exception) {
                _message.value = e.message ?: "Erreur inattendue"
            }
        }
    }

    fun clearMessage() {
        _message.value = null
    }
}
