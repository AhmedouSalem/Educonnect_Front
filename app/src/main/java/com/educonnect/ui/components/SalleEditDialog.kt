package com.educonnect.ui.components

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.educonnect.model.SalleDto
import com.educonnect.ui.theme.BlackColor
import com.educonnect.ui.theme.Secondary

@Composable
fun SalleEditDialog(
    context: Context,
    salle: SalleDto,
    campusList: List<String>,
    batimentList: List<String>,
    selectedCampus: String,
    selectedBatiment: String,
    onCampusSelected: (String) -> Unit,
    onBatimentSelected: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: (SalleDto) -> Unit
) {
    var capacite by remember { mutableStateOf(salle.capacite) }
    var type by remember { mutableStateOf(salle.type) }
    var etage by remember { mutableStateOf(salle.etage) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Modifier la salle ${salle.numero}") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CustomDropdown(
                    color = BlackColor,
                    label = "Campus",
                    items = campusList,
                    selectedItem = selectedCampus,
                    onItemSelected = onCampusSelected
                )

                CustomDropdown(
                    color = BlackColor,
                    label = "Bâtiment",
                    items = batimentList,
                    selectedItem = selectedBatiment,
                    onItemSelected = onBatimentSelected
                )

                CustomAdminFormTextField(
                    color = BlackColor,
                    value = capacite,
                    label = "Capacité",
                    onValueChange = { capacite = it }
                )

                CustomDropdown(
                    color = BlackColor,
                    label = "Type",
                    items = listOf("Amphi", "SC", "TD", "TP", "Numérique"),
                    selectedItem = type,
                    onItemSelected = { type = it }
                )

                CustomDropdown(
                    color = BlackColor,
                    label = "Étage",
                    items = listOf("0", "1", "2", "3", "4", "5"),
                    selectedItem = etage,
                    onItemSelected = { etage = it }
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                val updatedSalle = SalleDto(
                    numero = salle.numero,
                    capacite = capacite.trim(),
                    type = type.trim(),
                    etage = etage.trim(),
                    campusNom = selectedCampus,
                    batimentCode = selectedBatiment
                )
                onConfirm(updatedSalle)
            }) {
                Text("Confirmer")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    )
}
