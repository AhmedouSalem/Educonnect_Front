package com.educonnect.ui.parcours

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
import com.educonnect.model.ParcoursDto
import com.educonnect.repository.MentionRepository
import com.educonnect.repository.ParcoursService
import com.educonnect.ui.components.ParcoursDialog
import com.educonnect.ui.components.ScrollableFormLayout
import com.educonnect.ui.theme.Primary
import kotlinx.coroutines.launch


@Composable
fun ParcoursScreen(
    context: Context,
    navController: NavController,
    parcoursService: ParcoursService,
    mentionRepository: MentionRepository,
    onLogout: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val parcoursList = remember { mutableStateListOf<ParcoursDto>() }
    var showDialog by remember { mutableStateOf(false) }
    var editingParcours by remember { mutableStateOf<ParcoursDto?>(null) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val parcours = parcoursService.getAllParcours()
                parcoursList.clear()
                parcoursList.addAll(parcours)
            } catch (e: Exception) {
                Toast.makeText(context, "Erreur de chargement: ${e.message}", Toast.LENGTH_SHORT).show()
            }
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
                text = "Gestion des parcours",
                fontSize = 24.sp,
                modifier = Modifier.weight(1f),
                color = Primary,
                textAlign = TextAlign.Center
            )
            IconButton(onClick = {
                editingParcours = null
                showDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Ajouter", tint = Color.Black,
                    modifier = Modifier.weight(3f))
            }
        }

        // En-têtes du tableau
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Code", modifier = Modifier.weight(0.3f), fontSize = 16.sp, color = Primary)
            Text("Nom", modifier = Modifier.weight(0.5f), fontSize = 16.sp, color = Primary)
            Text("Actions", modifier = Modifier.weight(0.2f), fontSize = 16.sp, color = Primary)
        }

        // Corps du tableau
        parcoursList.forEach { parcours ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(parcours.code, modifier = Modifier.weight(0.3f))
                Text(parcours.name, modifier = Modifier.weight(0.5f))
                Row(
                    modifier = Modifier.weight(0.2f),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = {
                        editingParcours = parcours
                        showDialog = true
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Modifier", tint = Color.Black)
                    }
                    IconButton(onClick = {
                        coroutineScope.launch {
                            try {
                                parcoursService.deleteParcours(parcours.code)
                                parcoursList.remove(parcours)
                                Toast.makeText(context, "Parcours supprimé", Toast.LENGTH_SHORT).show()
                            } catch (e: Exception) {
                                Toast.makeText(context, "Erreur : ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Supprimer", tint = Color.Red)
                    }
                }
            }
        }

        if (showDialog) {
            ParcoursDialog(
                initialParcours = editingParcours,
                onDismiss = { showDialog = false },
                onSubmit = { parcoursDto ->
                    coroutineScope.launch {
                        try {
                            if (editingParcours == null) {
                                parcoursService.createParcours(parcoursDto)
                            } else {
                                parcoursService.updateParcours(parcoursDto.code, parcoursDto)
                            }
                            parcoursList.clear()
                            parcoursList.addAll(parcoursService.getAllParcours())
                            showDialog = false
                            Toast.makeText(context, "Sauvegarde réussie", Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            Toast.makeText(context, "Erreur : ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                mentionRepository = mentionRepository
            )
        }
    }
}
