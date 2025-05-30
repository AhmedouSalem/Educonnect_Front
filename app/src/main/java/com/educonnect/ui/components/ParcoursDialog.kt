package com.educonnect.ui.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.educonnect.model.MentionDto
import com.educonnect.model.ParcoursDto
import com.educonnect.repository.MentionRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParcoursDialog(
    initialParcours: ParcoursDto?,
    onDismiss: () -> Unit,
    onSubmit: (ParcoursDto) -> Unit,
    mentionRepository: MentionRepository
) {
    val coroutineScope = rememberCoroutineScope()

    var nomParcours by remember { mutableStateOf(initialParcours?.name ?: "") }
    var codeParcours by remember { mutableStateOf(initialParcours?.code ?: "") }

    var mentions by remember { mutableStateOf(listOf<MentionDto>()) }
    var selectedMention by remember { mutableStateOf<MentionDto?>(null) }
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                mentions = mentionRepository.getListMentions()
                if (selectedMention == null && mentions.isNotEmpty()) {
                    selectedMention = mentions.first()
                }
            } catch (e: Exception) {
                Log.e("ParcoursDialog", "Erreur de chargement des mentions : ${e.message}", e)
            }
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = if (initialParcours == null) "Ajouter un parcours" else "Modifier le parcours")
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = codeParcours,
                    onValueChange = { codeParcours = it },
                    label = { Text("Code du parcours") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = nomParcours,
                    onValueChange = { nomParcours = it },
                    label = { Text("Nom du parcours") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))

                if (mentions.isEmpty()) {
                    Text("Chargement des mentions...", modifier = Modifier.padding(8.dp))
                } else {
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            readOnly = true,
                            value = selectedMention?.intitule ?: "Choisir une mention",
                            onValueChange = {},
                            label = { Text("Mention associée") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            mentions.forEach { mention ->
                                DropdownMenuItem(
                                    text = { Text(mention.intitule) },
                                    onClick = {
                                        selectedMention = mention
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                enabled = selectedMention != null,
                onClick = {
                    coroutineScope.launch {
                        selectedMention?.let {
                            try {
                                // récupération complète (avec ID)
                                val mention = mentionRepository.getMentionByIntitule(it.intitule)
                                Log.d("ParcoursDialog", "Mention sélectionnée ID = ${mention.id}")
                                onSubmit(
                                    ParcoursDto(
                                        code = codeParcours,
                                        name = nomParcours,
                                        mentionId = mention.id // on envoie juste l'ID
                                    )
                                )
                            } catch (e: Exception) {
                                Log.e("ParcoursDialog", "Erreur lors de la récupération de l’ID : ${e.message}")
                            }
                        }
                    }
                }
            ) {
                Text("Valider")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    )
}
