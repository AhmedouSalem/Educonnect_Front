package com.educonnect.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.educonnect.model.MentionDto

@Composable
fun MentionDialog(
    initialMention: MentionDto?,
    onDismiss: () -> Unit,
    onSubmit: (MentionDto) -> Unit
) {
    var intitule by remember { mutableStateOf(initialMention?.intitule ?: "") }

    Dialog(onDismissRequest = { onDismiss() }) {
        CardBackgroundWrapper(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentPadding = 16.dp
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {

                // Titre blanc
                Text(
                    text = if (initialMention == null) "Ajouter une mention" else "Modifier une mention",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Champ de saisie avec texte et bordure blancs
                OutlinedTextField(
                    value = intitule,
                    onValueChange = { intitule = it },
                    label = { Text("Intitulé", color = Color.White) },
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

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    // Bouton Annuler blanc
                    OutlinedButton(onClick = onDismiss) {
                        Text("Annuler", color = Color.White)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    // Bouton Valider blanc
                    Button(onClick = {
                        onSubmit(MentionDto(id = initialMention?.id ?: 0, intitule = intitule))
                        onDismiss()
                    }) {
                        Text("Valider", color = Color.White)
                    }
                }
            }
        }
    }
}
