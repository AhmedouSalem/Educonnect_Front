package com.educonnect.ui.planning

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educonnect.domain.admin.AddPlanningUseCase
import com.educonnect.model.PlanningDto
import com.educonnect.repository.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class PlanningViewModel(
    private val mentionRepository: MentionRepository,
    private val parcoursRepository: ParcoursRepository,
    private val courseRepository: CourseRepository,
    private val campusRepository: CampusRepository,
    private val batimentRepository: BuildingRepository,
    private val salleRepository: SalleRepository,
    private val addPlanningUseCase: AddPlanningUseCase
) : ViewModel() {

    // Champs sélectionnés
    private val _mention = MutableStateFlow("")
    val mention: StateFlow<String> = _mention

    private val _parcours = MutableStateFlow("")
    val parcours: StateFlow<String> = _parcours

    private val _cours = MutableStateFlow("")
    val cours: StateFlow<String> = _cours

    private val _typeCours = MutableStateFlow("")
    val typeCours: StateFlow<String> = _typeCours

    private val _campus = MutableStateFlow("")
    val campus: StateFlow<String> = _campus

    private val _batiment = MutableStateFlow("")
    val batiment: StateFlow<String> = _batiment

    private val _salle = MutableStateFlow("")
    val salle: StateFlow<String> = _salle

    val calendar = Calendar.getInstance()
    
    private val _heureDebut = MutableStateFlow("")
    val heureDebut: StateFlow<String> = _heureDebut

    private val _heureFin = MutableStateFlow("")
    val heureFin: StateFlow<String> = _heureFin

    private val _date = MutableStateFlow("")
    val date: StateFlow<String> = _date

    // Messages utilisateur
    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    // Listes déroulantes
    private val _mentionList = MutableStateFlow<List<String>>(emptyList())
    val mentionList: StateFlow<List<String>> = _mentionList

    // était : List<String>
    private val _parcoursList = MutableStateFlow<List<Pair<String, String>>>(emptyList())
    val parcoursList: StateFlow<List<Pair<String, String>>> = _parcoursList

    private val _coursList = MutableStateFlow<List<Pair<String, String>>>(emptyList())
    val coursList: StateFlow<List<Pair<String, String>>> = _coursList



    private val _campusList = MutableStateFlow<List<String>>(emptyList())
    val campusList: StateFlow<List<String>> = _campusList

    private val _batimentList = MutableStateFlow<List<String>>(emptyList())
    val batimentList: StateFlow<List<String>> = _batimentList

    private val _salleList = MutableStateFlow<List<String>>(emptyList())
    val salleList: StateFlow<List<String>> = _salleList

    val typeCoursList = listOf("CM", "TD", "TP", "EVALUATION")

    init {
        chargerMentions()
        chargerCampus()
    }

    private fun chargerMentions() {
        viewModelScope.launch {
            _mentionList.value = mentionRepository.getAllMentions()
        }
    }

    private fun chargerParcours(mention: String) {
        viewModelScope.launch {
            _parcoursList.value = parcoursRepository.getParcoursByMention(mention)
                .map { it.code to it.name } // Pair(code, label)
        }
    }

    private fun chargerCours(parcours: String) {
        viewModelScope.launch {
            _coursList.value = courseRepository.getCoursesByParcours(parcours)
                .map { it.code to it.intitule }
        }
    }



    private fun chargerCampus() {
        viewModelScope.launch {
            _campusList.value = campusRepository.getAllCampus()
        }
    }

    private fun chargerBatiments(campus: String) {
        viewModelScope.launch {
            _batimentList.value = batimentRepository.getBatimentsByCampus(campus)
        }
    }

    private fun chargerSalles(batiment: String) {
        val campusName = campus.value
        if (campusName.isNotBlank()) {
            viewModelScope.launch {
                _salleList.value = salleRepository.getSallesByBatimentAndCampus(campusName, batiment)
            }
        }
    }


    // Mise à jour conditionnelle
    fun onMentionSelected(value: String) {
        _mention.value = value
        _parcours.value = ""
        _cours.value = ""
        chargerParcours(value)
    }

    fun onParcoursSelected(value: String) {
        _parcours.value = value
        _cours.value = ""
        chargerCours(value)
    }

    fun onCoursSelected(value: String) {
        _cours.value = value
    }

    fun onTypeCoursSelected(value: String) {
        _typeCours.value = value
    }

    fun onCampusSelected(value: String) {
        _campus.value = value
        _batiment.value = ""
        _salle.value = ""
        chargerBatiments(value)
    }

    fun onBatimentSelected(value: String) {
        _batiment.value = value
        _salle.value = ""
        chargerSalles(value)
    }

    fun onSalleSelected(value: String) {
        _salle.value = value
    }

    fun onHeureDebutChange(value: String) {
        _heureDebut.value = value
    }

    fun onHeureFinChange(value: String) {
        _heureFin.value = value
    }

    fun onDateChange(value: String) {
        _date.value = value
    }

    fun clearMessage() {
        _message.value = null
    }

    fun ajouterPlanning() {
        viewModelScope.launch {
            if (
                mention.value.isBlank() ||
                parcours.value.isBlank() ||
                cours.value.isBlank() ||
                typeCours.value.isBlank() ||
                campus.value.isBlank() ||
                batiment.value.isBlank() ||
                salle.value.isBlank() ||
                heureDebut.value.isBlank() ||
                heureFin.value.isBlank() ||
                date.value.isBlank()
            ) {
                _message.value = "Tous les champs sont obligatoires"
                return@launch
            }

            try {
                val dto = PlanningDto(
                    mention.value,
                    parcours.value,
                    cours.value,
                    typeCours.value,
                    campus.value,
                    batiment.value,
                    salle.value,
                    heureDebut.value,
                    heureFin.value,
                    date.value
                )

                val success = addPlanningUseCase(dto)

                _message.value = if (success) {
                    _mention.value = ""
                    _parcours.value = ""
                    _cours.value = ""
                    _typeCours.value = ""
                    _campus.value = ""
                    _batiment.value = ""
                    _salle.value = ""
                    _heureDebut.value = ""
                    _heureFin.value = ""
                    _date.value = ""
                    "Planning ajouté avec succès"
                } else {
                    "Erreur inconnue"
                }

            } catch (e: Exception) {
                _message.value = e.message ?: "Erreur inattendue"
            }
        }
    }
}
