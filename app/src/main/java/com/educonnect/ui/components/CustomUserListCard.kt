package com.educonnect.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.educonnect.model.UserRow
import com.educonnect.ui.components.CardBackgroundWrapper
import com.educonnect.ui.theme.Primary



@Composable
fun CustomUserListCard(
    title: String,
    users: List<UserRow>,
    isStudent: Boolean,
    onEdit: (UserRow) -> Unit,
    onDelete: (UserRow) -> Unit
) {
    val paginationState = remember { mutableStateOf(1) }
    val sortAscending = remember { mutableStateOf(true) }
    val sortedUsers = remember(users, sortAscending.value) {
        if (sortAscending.value) users.sortedBy { it.nom }
        else users.sortedByDescending { it.nom }
    }
    val usersPerPage = 5
    val totalPages = (sortedUsers.size + usersPerPage - 1) / usersPerPage
    val displayedUsers = sortedUsers.chunked(usersPerPage).getOrNull(paginationState.value - 1) ?: emptyList()

    CardBackgroundWrapper(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        contentPadding = 16.dp
    ) {
        Column {
            // Titre + bouton de tri
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Black,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier.padding(bottom = 8.dp),
                    textAlign = TextAlign.Center
                )
                IconButton(onClick = { sortAscending.value = !sortAscending.value }) {
                    Icon(
                        imageVector = Icons.Default.ArrowUpward,
                        contentDescription = "Trier",
                        tint = Color.Black,
                        modifier = Modifier.size(80.dp),

                    )
                }
            }

            // En-tête
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Nom", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, color = Primary)
                Text("Prénom", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, color = Primary)
                Text("INE", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, color = Primary)
                Text("Email", modifier = Modifier.weight(2f), fontWeight = FontWeight.Bold, color = Primary) // élargi
                Text("Parcours", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, color = Primary)
                if (isStudent) {
                    Text("Niveau", modifier = Modifier.weight(0.8f), fontWeight = FontWeight.Bold, color = Primary) // réduit
                }
                if (!isStudent) {
                    Text("Spécialité", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, color = Primary)
                    Text("Matières", modifier = Modifier.weight(2f), fontWeight = FontWeight.Bold, color = Primary)
                }
                Text("Actions", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, color = Primary)
            }


            Divider(modifier = Modifier.padding(vertical = 4.dp))

            // Données
            displayedUsers.forEach { user ->
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(user.nom, modifier = Modifier.weight(1f))
                    Text(user.prenom, modifier = Modifier.weight(1f))
                    Text(user.ine, modifier = Modifier.weight(1f))
                    Text(user.email, modifier = Modifier.weight(2f))
                    Text(user.parcours, modifier = Modifier.weight(1f))
                    if (isStudent) {
                        Text(user.niveau ?: "-", modifier = Modifier.weight(0.8f))
                    }
                    if (!isStudent) {
                        Text(user.specialite ?: "-", modifier = Modifier.weight(1f))
                        Text(user.matieres?.joinToString(", ") ?: "-", modifier = Modifier.weight(2f))
                    }
                    Row(modifier = Modifier.weight(1f)) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Modifier",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .clickable { onEdit(user) }
                        )
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Supprimer",
                            modifier = Modifier.clickable { onDelete(user) }
                        )
                    }
                }
            }

            // Pagination
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = { if (paginationState.value > 1) paginationState.value-- }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Page précédente")
                }
                Text("${paginationState.value}/$totalPages")
                IconButton(onClick = { if (paginationState.value < totalPages) paginationState.value++ }) {
                    Icon(Icons.Default.ArrowForward, contentDescription = "Page suivante")
                }
            }
        }
    }
}

