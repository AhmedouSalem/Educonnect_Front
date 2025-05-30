package com.educonnect.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.educonnect.model.UserCreationRequest
import com.educonnect.model.UserRow
import com.educonnect.repository.UserRepository
import com.educonnect.ui.components.CustomAdminFormTextField
import kotlinx.coroutines.launch


@Composable
fun EditUserDialog(
    user: UserRow,
    isStudent: Boolean,
    userService: UserRepository, //  injection du service
    onDismiss: () -> Unit,
    onSubmit: (UserCreationRequest) -> Unit
) {
    val scope = rememberCoroutineScope()

    var nom by remember { mutableStateOf(user.nom) }
    var prenom by remember { mutableStateOf(user.prenom) }
    var email by remember { mutableStateOf(user.email) }
    var codeIne by remember { mutableStateOf(user.ine) }
    var parcours by remember { mutableStateOf(user.parcours) }
    var niveau by remember { mutableStateOf(user.niveau) }
    var specialite by remember { mutableStateOf(user.specialite ?: "") }
    var matieres by remember { mutableStateOf(user.matieres?.joinToString(", ") ?: "") }

    val parcoursList = remember { mutableStateListOf<String>() }
    val mentionList = remember { mutableStateListOf<String>() }

    // ✅ Chargement des listes comme dans AddUserScreen
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
                // Gérer l’erreur si nécessaire
            }
        }
    }

    Dialog(onDismissRequest = { onDismiss() }) {
        CardBackgroundWrapper(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentPadding = 16.dp
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {

                CustomAdminAddPageTitleTextView(text = "Modifier ${if (isStudent) "un étudiant" else "un professeur"}")
                Spacer(modifier = Modifier.height(12.dp))

                CustomAdminFormTextField(value = nom, label = "Nom") { nom = it }
                CustomAdminFormTextField(value = prenom, label = "Prénom") { prenom = it }
                CustomAdminFormTextField(value = email, label = "Email") { email = it }
                CustomAdminFormTextField(value = codeIne, label = "Code INE") { codeIne = it }

                if (isStudent) {
                    CustomDropdown(
                        label = "Parcours",
                        items = parcoursList,
                        selectedItem = parcours,
                        onItemSelected = { parcours = it }
                    )
                    CustomDropdown(
                        label = "Niveau",
                        items = mentionList,
                        selectedItem = niveau.toString(),
                        onItemSelected = { niveau = it }
                    )
                } else {
                    CustomAdminFormTextField(value = parcours, label = "Parcours") { parcours = it }
                    CustomAdminFormTextField(value = specialite, label = "Spécialité") { specialite = it }
                    CustomAdminFormTextField(
                        value = matieres,
                        label = "Matières (séparées par des virgules)"
                    ) { matieres = it }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(onClick = onDismiss) {
                        Text("Annuler")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        val request = UserCreationRequest(
                            nom = nom,
                            prenom = prenom,
                            email = email,
                            password = "placeholder",
                            codeINE = codeIne,
                            role = if (isStudent) "ETUDIANT" else "PROFESSEUR",
                            parcours = parcours,
                            niveau = niveau.takeIf { isStudent },
                            specialite = specialite.takeIf { !isStudent },
                            matieres = if (!isStudent && matieres.isNotBlank())
                                matieres.split(",").map { it.trim() }
                            else null
                        )
                        onSubmit(request)
                        onDismiss()
                    }) {
                        Text("Valider")
                    }
                }
            }
        }
    }
}
