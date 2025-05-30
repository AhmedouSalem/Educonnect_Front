package com.educonnect.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.educonnect.model.MentionDto


@Composable
fun MentionDialog(
    initialMention: MentionDto?,
    onDismiss: () -> Unit,
    onSubmit: (MentionDto) -> Unit
) {
    var intitule by remember { mutableStateOf(initialMention?.intitule ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = if (initialMention == null) "Ajouter une mention" else "Modifier une mention") },
        text = {
            OutlinedTextField(
                value = intitule,
                onValueChange = { intitule = it },
                label = { Text("Intitulé") },
                singleLine = true
            )
        },
        confirmButton = {
            Button(onClick = {
                onSubmit(MentionDto(id = initialMention?.id ?: 0, intitule = intitule))
            }) {
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
