package com.educonnect.ui.users

import CustomTopAppBar
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.educonnect.model.UserCreationRequest
import com.educonnect.repository.MentionService
import com.educonnect.repository.ParcoursService
import com.educonnect.repository.UserRepository
import com.educonnect.ui.components.*
import com.educonnect.ui.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun AddUserScreen(
    context: Context,
    navController: NavController,
    userService: UserRepository,
    onLogout: () -> Unit
) {
    val roleUiToBackend = mapOf(
        "Étudiant" to "ETUDIANT",
        "Professeur" to "PROFESSEUR"
    )

    val scope = rememberCoroutineScope()

    var nom by remember { mutableStateOf("") }
    var prenom by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var codeIne by remember { mutableStateOf("") }
    var selectedRoleUi by remember { mutableStateOf("") }
    val roleBackend = roleUiToBackend[selectedRoleUi] ?: ""
    var parcours by remember { mutableStateOf("") }
    var niveau by remember { mutableStateOf("") } // Pour Étudiant
    var specialite by remember { mutableStateOf("") } // Pour Professeur
    var matieresInput by remember { mutableStateOf("") } // Pour Professeur

    val parcoursList = remember { mutableStateListOf<String>() }
    val mentionList = remember { mutableStateListOf<String>() }

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val parcoursListResult = userService.getAllParcours()
                val mentionListResult = userService.getAllMentions()

                parcoursList.clear()
                parcoursList.addAll(parcoursListResult.map { it.code })

                mentionList.clear()
                mentionList.addAll(mentionListResult.map { it.intitule })

            } catch (e: Exception) {
                Toast.makeText(context, "Erreur chargement données: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }


    ScrollableFormLayout(
        topContent = {
            CustomTopAppBar(
                onHomeClick = { navController.navigate(Screen.AdminHome.route) },
                onSearch = { },
                onProfileClick = { },
                onLogoutClick = { onLogout() }
            )
        }
    ) {

        //Voir liste des listes
        CustomAdminAddButton(
            buttonText = "Voir liste des listes",
            onAddClick = { navController.navigate(Screen.ListUsers.route) },
        )

        CustomAdminAddPageTitleTextView(text = "Ajout d’un utilisateur")

        CustomAdminFormTextField(value = nom, label = "Nom") { nom = it }
        CustomAdminFormTextField(value = prenom, label = "Prénom") { prenom = it }
        CustomAdminFormTextField(value = email, label = "Email") { email = it }
        CustomAdminFormTextField(value = password, label = "Mot de passe") { password = it }

        CustomDropdown(
            label = "Rôle",
            items = listOf("Étudiant", "Professeur"),
            selectedItem = selectedRoleUi,
            onItemSelected = { selectedRoleUi = it }
        )

        CustomAdminFormTextField(value = codeIne, label = "Code INE") { codeIne = it }

        if (selectedRoleUi == "Étudiant") {
            CustomDropdown(
                label = "Parcours",
                items = parcoursList,
                selectedItem = parcours,
                onItemSelected = { parcours = it }
            )
            CustomDropdown(
                label = "Niveau",
                items = mentionList,
                selectedItem = niveau,
                onItemSelected = { niveau = it }
            )
        }

        if (selectedRoleUi == "Professeur") {
            // Ici, on remplace la liste déroulante par un champ texte
            CustomAdminFormTextField(value = parcours, label = "Parcours") { parcours = it }

            CustomAdminFormTextField(value = specialite, label = "Spécialité") { specialite = it }

            CustomAdminFormTextField(
                value = matieresInput,
                label = "Matières (séparées par des virgules)"
            ) { matieresInput = it }
        }


        CustomAdminAddButton(
            buttonText = "Ajouter",
            onAddClick = {
                if (codeIne.isBlank()) {
                    Toast.makeText(context, "Veuillez remplir le code INE", Toast.LENGTH_SHORT).show()
                    return@CustomAdminAddButton
                }
                val user = UserCreationRequest(
                    nom = nom,
                    prenom = prenom,
                    email = email,
                    password = password,
                    codeINE = codeIne,
                    role = roleBackend,
                    parcours = parcours.takeIf { it.isNotBlank() },
                    niveau = niveau.takeIf { selectedRoleUi == "Étudiant" },
                    specialite = specialite.takeIf { selectedRoleUi == "Professeur" },
                    matieres = if (selectedRoleUi == "Professeur" && matieresInput.isNotBlank())
                        matieresInput.split(",").map { it.trim() }
                    else null
                )

                scope.launch {
                    try {
                        userService.addUser(user)
                        Log.d("Données envoyées", user.toString())
                        Toast.makeText(context, "Ajout d'utilisateur réussi", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(context, "Erreur : ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        )
    }
}
