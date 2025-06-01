package com.educonnect.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.educonnect.model.CampusDto

@Composable
fun CampusEditDialog(
    initialCampus: CampusDto,
    onDismiss: () -> Unit,
    onConfirm: (CampusDto) -> Unit
) {
    var nom by remember { mutableStateOf(initialCampus.nom) }
    var ville by remember { mutableStateOf(initialCampus.ville) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                onConfirm(CampusDto(nom.trim(), ville.trim()))
            }) {
                Text("Modifier")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = { onDismiss() }) {
                Text("Annuler")
            }
        },
        title = { Text("Modifier le campus") },
        text = {
            Column {
                OutlinedTextField(
                    value = nom,
                    onValueChange = { nom = it },
                    label = { Text("Nom") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = ville,
                    onValueChange = { ville = it },
                    label = { Text("Ville") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}
