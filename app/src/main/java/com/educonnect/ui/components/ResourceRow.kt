package com.educonnect.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.educonnect.model.ResourceDto


@Composable
fun ResourceRow(resource: ResourceDto, onDelete: () -> Unit) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = "Nom : ${resource.nom}", color = Color.White)
            Text(text = "Type : .${resource.type.lowercase()}", color = Color.White)
        }

        Row {
            IconButton(onClick = { /* Modifier */ }) {
                Icon(Icons.Default.Edit, contentDescription = "Modifier", tint = Color.Yellow)
            }
            IconButton(onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(resource.url))
                context.startActivity(intent, null)
            }) {
                Icon(Icons.Default.Download, contentDescription = "Télécharger", tint = Color.Yellow)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Supprimer", tint = Color.Yellow)
            }
        }
    }
}
