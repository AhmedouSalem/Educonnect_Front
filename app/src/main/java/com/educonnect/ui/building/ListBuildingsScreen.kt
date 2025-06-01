package com.educonnect.ui.building

import CustomTopAppBar
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.educonnect.di.Injection
import com.educonnect.model.BatimentDto
import com.educonnect.ui.components.BuildingEditDialog
import com.educonnect.ui.components.ScrollableFormLayout
import com.educonnect.ui.theme.Primary
import kotlinx.coroutines.launch
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import com.educonnect.ui.components.scrollbar


@Composable
fun ListBuildingsScreen(
    context: Context,
    navController: NavController,
    onLogout: () -> Unit,
    onNavigateToAddBuilding: () -> Unit,
    viewModel: BuildingViewModel = remember { Injection.provideBuildingViewModel(context) }
) {
    val coroutineScope = rememberCoroutineScope()
    val buildingList = remember { mutableStateListOf<BatimentDto>() }

    var showEditDialog by remember { mutableStateOf(false) }
    var editingBuilding by remember { mutableStateOf<BatimentDto?>(null) }
    var oldBuilding by remember { mutableStateOf<BatimentDto?>(null) }

    var showDeleteDialog by remember { mutableStateOf(false) }
    var buildingToDelete by remember { mutableStateOf<BatimentDto?>(null) }

    val campusList by viewModel.campusList.collectAsState()

    val scrollState = rememberScrollState()

    // Initial load
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val buildings = Injection.provideBuildingRepository().getAllBuildings()
            buildingList.clear()
            buildingList.addAll(buildings)
        }
    }

    ScrollableFormLayout(
        topContent = {
            CustomTopAppBar(
                onHomeClick = { navController.popBackStack() },
                onSearch = { /* TODO */ },
                onProfileClick = { /* TODO */ },
                onLogoutClick = { onLogout() }
            )
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Liste des bâtiments",
                fontSize = 24.sp,
                modifier = Modifier.weight(1f),
                color = Primary,
                textAlign = TextAlign.Center
            )
            IconButton(onClick = { onNavigateToAddBuilding() }) {
                Icon(Icons.Default.Add, contentDescription = "Ajouter", tint = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
                .scrollbar(scrollState)
        ) {
            Column {
                // En-têtes du tableau
                Row(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "codeB",
                        modifier = Modifier.width(120.dp),
                        fontSize = 16.sp,
                        color = Primary
                    )
                    Text(
                        "AnnéeC",
                        modifier = Modifier.width(100.dp),
                        fontSize = 16.sp,
                        color = Primary
                    )
                    Text(
                        "Campus",
                        modifier = Modifier.width(120.dp),
                        fontSize = 16.sp,
                        color = Primary
                    )
                    Text(
                        "Actions",
                        modifier = Modifier.width(100.dp),
                        fontSize = 16.sp,
                        color = Primary
                    )
                }

                buildingList.forEach { building ->
                    Row(
                        modifier = Modifier
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(building.codeB, modifier = Modifier.width(120.dp))
                        Text(building.anneC, modifier = Modifier.width(100.dp))
                        Text(building.campusNom, modifier = Modifier.width(120.dp))

                        Row(
                            modifier = Modifier.width(100.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
                        ) {
                            IconButton(onClick = {
                                editingBuilding = building
                                oldBuilding = building
                                showEditDialog = true
                            }) {
                                Icon(
                                    Icons.Default.Edit,
                                    contentDescription = "Modifier",
                                    tint = Color.Black
                                )
                            }
                            IconButton(onClick = {
                                buildingToDelete = building
                                showDeleteDialog = true
                            }) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Supprimer",
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }

        // 🔧 Dialog de modification
        if (showEditDialog && editingBuilding != null) {
            BuildingEditDialog(
                initialBatiment = editingBuilding!!,
                campusOptions = campusList,
                onDismiss = { showEditDialog = false },
                onConfirm = { updated ->
                    coroutineScope.launch {
                        val success = Injection.provideUpdateBuildingUseCase(context)(
                            oldBuilding!!,
                            updated
                        )
                        if (success) {
                            buildingList.remove(editingBuilding)
                            buildingList.add(updated)
                            Toast.makeText(context, "Bâtiment modifié", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Erreur de modification", Toast.LENGTH_SHORT)
                                .show()
                        }
                        showEditDialog = false
                    }
                }
            )
        }

        // Confirmation de suppression
        if (showDeleteDialog && buildingToDelete != null) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                confirmButton = {
                    Button(onClick = {
                        coroutineScope.launch {
                            val success =
                                Injection.provideDeleteBuildingUseCase(context)(buildingToDelete!!.campusNom,buildingToDelete!!.codeB)
                            if (success) {
                                buildingList.remove(buildingToDelete)
                                Toast.makeText(context, "Bâtiment supprimé", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                Toast.makeText(context, "Erreur suppression", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            showDeleteDialog = false
                        }
                    }) {
                        Text("Confirmer")
                    }
                },
                dismissButton = {
                    OutlinedButton(onClick = { showDeleteDialog = false }) {
                        Text("Annuler")
                    }
                },
                title = { Text("Confirmer la suppression") },
                text = { Text("Supprimer le bâtiment « ${buildingToDelete?.codeB} » ?") }
            )
        }
    }
}

