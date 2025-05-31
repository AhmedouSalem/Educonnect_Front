package com.educonnect.ui.campus

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
import com.educonnect.model.CampusDto
import com.educonnect.ui.components.CampusEditDialog
import com.educonnect.ui.components.ScrollableFormLayout
import com.educonnect.ui.theme.Primary
import kotlinx.coroutines.launch

@Composable
fun ListCampusScreen(
    context: Context,
    navController: NavController,
    onLogout: () -> Unit,
    onNavigateToAddCampus: () -> Unit,
    viewModel: CampusViewModel = remember { Injection.provideCampusViewModel(context) }
) {
    val coroutineScope = rememberCoroutineScope()
    val campusList = remember { mutableStateListOf<CampusDto>() }
    var showDialog by remember { mutableStateOf(false) }
    var editingCampus by remember { mutableStateOf<CampusDto?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var campusToDelete by remember { mutableStateOf<CampusDto?>(null) }



    // Simule un chargement initial
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val result = Injection.provideCampusRepository(context).getAllCampusDto()
            campusList.clear()
            campusList.addAll(result)
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
                text = "Liste des campus",
                fontSize = 24.sp,
                modifier = Modifier.weight(1f),
                color = Primary,
                textAlign = TextAlign.Center
            )
            IconButton(onClick = { onNavigateToAddCampus() }) {
                Icon(Icons.Default.Add, contentDescription = "Ajouter", tint = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        campusList.forEach { campus ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(campus.nom, modifier = Modifier.weight(0.4f))
                Text(campus.ville, modifier = Modifier.weight(0.4f))

                Row(
                    modifier = Modifier.weight(0.2f),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = {
                        editingCampus = campus
                        showDialog = true
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Modifier", tint = Color.Black)
                    }

                    IconButton(onClick = {
                        campusToDelete = campus
                        showDeleteDialog = true
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Supprimer", tint = Color.Red)
                    }
                }
            }
        }
        if (showDialog && editingCampus != null) {
            CampusEditDialog(
                initialCampus = editingCampus!!,
                onDismiss = { showDialog = false },
                onConfirm = { updatedCampus ->
                    coroutineScope.launch {
                        val updateUseCase = Injection.provideUpdateCampusUseCase(context)
                        val success = updateUseCase(editingCampus!!.nom, updatedCampus)
                        if (success) {
                            campusList.remove(editingCampus)
                            campusList.add(updatedCampus)
                            Toast.makeText(context, "Campus modifié", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Erreur de modification", Toast.LENGTH_SHORT).show()
                        }
                        showDialog = false
                    }
                }
            )
        }

        if (showDeleteDialog && campusToDelete != null) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                confirmButton = {
                    Button(onClick = {
                        coroutineScope.launch {
                            val deleteUseCase = Injection.provideDeleteCampusUseCase(context)
                            val success = deleteUseCase(campusToDelete!!.nom)
                            if (success) {
                                campusList.remove(campusToDelete)
                                Toast.makeText(context, "Campus supprimé", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Erreur lors de la suppression", Toast.LENGTH_SHORT).show()
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
                text = { Text("Êtes-vous sûr de vouloir supprimer le campus « ${campusToDelete?.nom} » ?") }
            )
        }


    }
}
