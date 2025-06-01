package com.educonnect.ui.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.educonnect.model.CourseDto
import com.educonnect.repository.ParcoursService
import com.educonnect.repository.UserRepository
import kotlinx.coroutines.launch
import com.google.accompanist.flowlayout.FlowRow


@Composable
fun CourseDialog(
    context: Context,
    initialCourse: CourseDto?,
    onDismiss: () -> Unit,
    onSubmit: (CourseDto) -> Unit,
    parcoursService: ParcoursService,
    userRepository: UserRepository,
    connectedTeacherId: Long
) {
    val coroutineScope = rememberCoroutineScope()

    var code by remember { mutableStateOf(TextFieldValue(initialCourse?.code ?: "")) }
    var intitule by remember { mutableStateOf(TextFieldValue(initialCourse?.intitule ?: "")) }
    var description by remember { mutableStateOf(TextFieldValue(initialCourse?.description ?: "")) }

    val parcoursList = remember { mutableStateListOf<String>() }
    val selectedParcours = remember { mutableStateListOf<String>().apply {
        initialCourse?.parcours?.let { addAll(it) }
    }}

    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                parcoursList.clear()
                parcoursList.addAll(parcoursService.getAllParcours().map { it.code })
            } catch (e: Exception) {
                Toast.makeText(context, "Erreur chargement parcours", Toast.LENGTH_SHORT).show()
            } finally {
                isLoading = false
            }
        }
    }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(
                onClick = {
                    if (code.text.isBlank() || intitule.text.isBlank() || selectedParcours.isEmpty()) {
                        Toast.makeText(context, "Champs obligatoires manquants", Toast.LENGTH_SHORT).show()
                        return@TextButton
                    }

                    onSubmit(
                        CourseDto(
                            id = initialCourse?.id,
                            code = code.text,
                            intitule = intitule.text,
                            description = description.text,
                            teacherId = connectedTeacherId,
                            parcours = selectedParcours,
                        )
                    )
                },
                enabled = !isLoading
            ) {
                Text("Soumettre")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Annuler")
            }
        },
        title = { Text(if (initialCourse == null) "Ajouter un cours" else "Modifier le cours") },
        text = {
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = code, onValueChange = { code = it }, label = { Text("Code") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = intitule, onValueChange = { intitule = it }, label = { Text("Intitulé") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth())

                Text("Parcours", style = MaterialTheme.typography.bodyMedium)

                FlowRow(
                    mainAxisSpacing = 8.dp,
                    crossAxisSpacing = 8.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    parcoursList.forEach { code ->
                        Row(
                            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = selectedParcours.contains(code),
                                onCheckedChange = {
                                    if (it) selectedParcours.add(code) else selectedParcours.remove(code)
                                }
                            )
                            Text(code)
                        }
                    }
                }

            }
        }
    )
}
