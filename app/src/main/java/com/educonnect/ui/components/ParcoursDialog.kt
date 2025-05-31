package com.educonnect.ui.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
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

    Dialog(onDismissRequest = onDismiss) {
        CardBackgroundWrapper(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentPadding = 16.dp
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {

                Text(
                    text = if (initialParcours == null) "Ajouter un parcours" else "Modifier le parcours",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = codeParcours,
                    onValueChange = { codeParcours = it },
                    label = { Text("Code du parcours", color = Color.White) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White,
                        cursorColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = nomParcours,
                    onValueChange = { nomParcours = it },
                    label = { Text("Nom du parcours", color = Color.White) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White,
                        cursorColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (mentions.isEmpty()) {
                    Text("Chargement des mentions...", modifier = Modifier.padding(8.dp), color = Color.White)
                } else {
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            readOnly = true,
                            value = selectedMention?.intitule ?: "Choisir une mention",
                            onValueChange = {},
                            label = { Text("Mention associée", color = Color.White) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            textStyle = LocalTextStyle.current.copy(color = Color.White),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.White,
                                unfocusedBorderColor = Color.White,
                                focusedLabelColor = Color.White,
                                unfocusedLabelColor = Color.White,
                                cursorColor = Color.White
                            )
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            mentions.forEach { mention ->
                                DropdownMenuItem(
                                    text = { Text(mention.intitule, color = Color.Black) }, // lisibilité en menu
                                    onClick = {
                                        selectedMention = mention
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(onClick = onDismiss) {
                        Text("Annuler", color = Color.White)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        enabled = selectedMention != null,
                        onClick = {
                            coroutineScope.launch {
                                selectedMention?.let {
                                    try {
                                        val mention = mentionRepository.getMentionByIntitule(it.intitule)
                                        onSubmit(
                                            ParcoursDto(
                                                code = codeParcours,
                                                name = nomParcours,
                                                mentionId = mention.id
                                            )
                                        )
                                        onDismiss()
                                    } catch (e: Exception) {
                                        Log.e("ParcoursDialog", "Erreur récupération ID mention : ${e.message}")
                                    }
                                }
                            }
                        }
                    ) {
                        Text("Valider", color = Color.White)
                    }
                }
            }
        }
    }
}
