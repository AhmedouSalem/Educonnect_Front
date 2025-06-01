package com.educonnect.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.educonnect.model.BatimentDto
import com.educonnect.ui.theme.BlackColor

@Composable
fun BuildingEditDialog(
    initialBatiment: BatimentDto,
    campusOptions: List<String>,
    onDismiss: () -> Unit,
    onConfirm: (BatimentDto) -> Unit
) {
    var code by remember { mutableStateOf(initialBatiment.codeB) }
    var anneC by remember { mutableStateOf(initialBatiment.anneC) }
    var selectedCampus by remember { mutableStateOf(initialBatiment.campusNom) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                onConfirm(BatimentDto(code, anneC, selectedCampus))
            }) {
                Text("Modifier")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Annuler")
            }
        },
        title = { Text("Modifier le bâtiment") },
        text = {
            Column {
                OutlinedTextField(
                    value = code,
                    onValueChange = { code = it },
                    label = { Text("Code") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = anneC,
                    onValueChange = { anneC = it },
                    label = { Text("Année de construction") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Menu déroulant pour campus
                var expanded by remember { mutableStateOf(false) }

                CustomDropdown(
                    color = BlackColor,
                    label = "Campus",
                    items = campusOptions,
                    selectedItem = selectedCampus,
                    onItemSelected = { selectedCampus = it }
                )

            }
        }
    )
}
