package com.educonnect.ui.mentions

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
import com.educonnect.model.MentionDto
import com.educonnect.repository.MentionService
import com.educonnect.ui.components.MentionDialog
import com.educonnect.ui.components.ScrollableFormLayout
import com.educonnect.ui.theme.Primary
import kotlinx.coroutines.launch

@Composable
fun MentionScreen(
    context: Context,
    navController: NavController,
    mentionService: MentionService,
    onLogout: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val mentionList = remember { mutableStateListOf<MentionDto>() }
    var showDialog by remember { mutableStateOf(false) }
    var editingMention by remember { mutableStateOf<MentionDto?>(null) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                mentionList.clear()
                mentionList.addAll(mentionService.getAllMentions())
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
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Gestion des Niveaux d'études",
                fontSize = 24.sp,
                modifier = Modifier.weight(1f),
                color = Primary,
                textAlign = TextAlign.Center
            )
            IconButton(onClick = {
                editingMention = null
                showDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Ajouter", tint = Color.Black)
            }
        }

        mentionList.forEach { mention ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = mention.intitule, modifier = Modifier.weight(1f))
                IconButton(onClick = {
                    editingMention = mention
                    showDialog = true
                }) {
                    Icon(Icons.Default.Edit, contentDescription = "Modifier", tint = Color.Black)
                }
                IconButton(onClick = {
                    coroutineScope.launch {
                        try {
                            mentionService.deleteMention(mention.id)
                            mentionList.remove(mention)
                            Toast.makeText(context, "Niveau supprimée", Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            Toast.makeText(context, "Erreur : ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }) {
                    Icon(Icons.Default.Delete, contentDescription = "Supprimer", tint = Color.Red)
                }
            }
        }

        if (showDialog) {
            MentionDialog(
                initialMention = editingMention,
                onDismiss = { showDialog = false },
                onSubmit = { mentionDto ->
                    coroutineScope.launch {
                        try {
                            val updatedMention = if (mentionDto.id.toInt() == 0)
                                mentionService.createMention(mentionDto)
                            else
                                mentionService.updateMention(mentionDto.id, mentionDto)

                            // Refresh liste
                            mentionList.clear()
                            mentionList.addAll(mentionService.getAllMentions())
                            showDialog = false
                            Toast.makeText(context, "Sauvegarde réussie", Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            Toast.makeText(context, "Erreur : ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            )
        }
    }
}
