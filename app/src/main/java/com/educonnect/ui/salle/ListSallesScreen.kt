package com.educonnect.ui.salle

import CustomTopAppBar
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.educonnect.di.Injection
import com.educonnect.model.SalleDto
import com.educonnect.ui.components.ScrollableFormLayout
import com.educonnect.ui.theme.Primary
import kotlinx.coroutines.launch
import androidx.compose.ui.text.style.TextAlign
import com.educonnect.ui.components.SalleEditDialog
import com.educonnect.ui.components.scrollbar

@Composable
fun ListSallesScreen(
    context: Context,
    onLogout: () -> Unit,
    onBackClick: () -> Unit,
    onNavigateToAddSalle: () -> Unit,
    viewModel: SalleListViewModel = remember { Injection.provideSalleListViewModel(context) },
    salleViewModel: SalleViewModel = remember { Injection.provideSalleViewModel(context) }
) {
    val salles by viewModel.salles.collectAsState()
    val message by viewModel.message.collectAsState()

    var showEditDialog by remember { mutableStateOf(false) }
    var editingSalle by remember { mutableStateOf<SalleDto?>(null) }
    var ancienneSalle by remember { mutableStateOf<SalleDto?>(null)}

    var showDeleteDialog by remember { mutableStateOf(false) }
    var salleToDelete by remember { mutableStateOf<SalleDto?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.loadSalles()
        salleViewModel.clearMessage()
    }

    ScrollableFormLayout(
        topContent = {
            CustomTopAppBar(
                onHomeClick = onBackClick,
                onSearch = {},
                onProfileClick = {},
                onLogoutClick = onLogout
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
                text = "Liste des salles",
                fontSize = 24.sp,
                modifier = Modifier.weight(1f),
                color = Primary,
                textAlign = TextAlign.Center
            )
            IconButton(onClick = onNavigateToAddSalle) {
                Icon(Icons.Default.Add, contentDescription = "Ajouter", tint = Color.Black)
            }
        }

        Box(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .scrollbar(scrollState)
        ) {
            Column(
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .padding(end = 16.dp) // marge pour le scroll
            ) {
                // En-tête
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Numéro", Modifier.weight(0.2f), color = Primary)
                    Text("Capacité", Modifier.weight(0.15f), color = Primary)
                    Text("Type", Modifier.weight(0.15f), color = Primary)
                    Text("Étage", Modifier.weight(0.1f), color = Primary)
                    Text("Campus", Modifier.weight(0.2f), color = Primary)
                    Text("Bâtiment", Modifier.weight(0.2f), color = Primary)
                    Text("Actions", Modifier.weight(0.2f), color = Primary)
                }

                // Contenu
                salles.forEach { salle ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(salle.numero, Modifier.weight(0.2f))
                        Text(salle.capacite, Modifier.weight(0.15f))
                        Text(salle.type, Modifier.weight(0.15f))
                        Text(salle.etage, Modifier.weight(0.1f))
                        Text(salle.campusNom, Modifier.weight(0.2f))
                        Text(salle.batimentCode, Modifier.weight(0.2f))
                        Row(
                            modifier = Modifier.weight(0.2f),
                            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
                        ) {
                            IconButton(onClick = {
                                editingSalle = salle
                                ancienneSalle = salle
                                salleViewModel.onCampusSelected(salle.campusNom)
                                salleViewModel.onBatimentNomChange(salle.batimentCode)
                                showEditDialog = true
                            }) {
                                Icon(
                                    Icons.Default.Edit,
                                    contentDescription = "Modifier",
                                    tint = Color.Black
                                )
                            }
                            IconButton(onClick = {
                                salleToDelete = salle
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

        if (showEditDialog && editingSalle != null) {
            SalleEditDialog(
                context = context,
                salle = editingSalle!!,
                campusList = salleViewModel.campusList.collectAsState().value,
                batimentList = salleViewModel.batimentList.collectAsState().value,
                selectedCampus = salleViewModel.campus.collectAsState().value,
                selectedBatiment = salleViewModel.batimentCode.collectAsState().value,
                onCampusSelected = salleViewModel::onCampusSelected,
                onBatimentSelected = salleViewModel::onBatimentNomChange,
                onDismiss = { showEditDialog = false },
                onConfirm = { updatedSalle ->
                    viewModel.updateSalle(ancienneSalle!!, updatedSalle) { success ->
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                if (success) "Salle modifiée avec succès" else "Erreur de modification"
                            )
                        }
                    }
                    showEditDialog = false
                }
            )
        }

        if (showDeleteDialog && salleToDelete != null) {
            ConfirmDeleteDialog(
                title = "Supprimer la salle",
                message = "Voulez-vous vraiment supprimer la salle ${salleToDelete!!.numero} ?",
                onDismiss = { showDeleteDialog = false },
                onConfirm = {
                    viewModel.deleteSalle(
                        salleToDelete!!.numero,
                        salleToDelete!!.campusNom,
                        salleToDelete!!.batimentCode
                    ) { success ->
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                if (success) "Salle supprimée" else "Erreur de suppression"
                            )
                        }
                        showDeleteDialog = false
                    }
                }
            )
        }

        SnackbarHost(hostState = snackbarHostState)
    }
}


@Composable
fun ConfirmDeleteDialog(
    title: String,
    message: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = {
            Button(onClick = onConfirm) {
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
