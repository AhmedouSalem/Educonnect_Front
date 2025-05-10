package com.educonnect.ui.home

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
fun TeacherHomeScreen(
    context: Context,
    onLogout: () -> Unit
) {
    val sessionManager = remember { SessionManager(context) }
    val authenticationResponse = sessionManager.getUserData()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenue, Enseignant",
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        authenticationResponse?.let {
            Text(text = "ID Utilisateur : ${it.userId}")
            Text(text = "Rôle : ${it.role}")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            sessionManager.clearUserData()
            onLogout()
        }) {
            Text(text = "Se déconnecter")
        }
    }
}
