package com.educonnect.ui.home

import TopAppBar
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.educonnect.utils.SessionManager

@Composable
fun StudentHomeScreen(
    context: Context,
    onLogout: () -> Unit
) {
    val sessionManager = remember { SessionManager(context) }
    val userData = sessionManager.getUserData()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            onHomeClick = { /* TODO: Handle Home Click */ },
            onSearch = { /* TODO: Handle Search Click */ },
            onProfileClick = { /* TODO: Handle Profile Click */ },
            onLogoutClick = {
                /* TODO: Handle Logout Click */
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "Bienvenue, Étudiant", fontSize = 24.sp)

        Button(onClick = onLogout) {
            Text(text = "Se déconnecter")
        }
    }
}
