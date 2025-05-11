package com.educonnect.ui.home

import TopAppBar
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.educonnect.R
import com.educonnect.ui.components.CustomTextView
import com.educonnect.ui.components.CustomWelcomeCard
import com.educonnect.ui.theme.Primary
import com.educonnect.utils.SessionManager

@Composable
fun AdminHomeScreen(
    context: Context,
    onLogout: () -> Unit
) {
    val sessionManager = remember { SessionManager(context) }
    val userData = sessionManager.getUserData()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent)
    ) {

        // Background Image
        Image(
            painter = painterResource(id = R.drawable.app_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Top AppBar
            TopAppBar(
                onHomeClick = { /* TODO: Handle Home Click */ },
                onSearch = { /* TODO: Handle Search Click */ },
                onProfileClick = { /* TODO: Handle Profile Click */ },
                onLogoutClick = {
                    sessionManager.clearUserData()
                    onLogout()
                }
            )

            Spacer(modifier = Modifier.height(40.dp))

            CustomWelcomeCard(userData?.role ?: "")

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* TODO: Ajouter un utilisateur */ },
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp)
                ) {
                CustomTextView(text = stringResource(R.string.ajouter_un_utilisateur))
            }

            Button(
                onClick = { /* TODO: Ajouter un planning */ },
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp)
            ) {
                CustomTextView(text = stringResource(R.string.ajouter_un_planning))
            }

            Image(
                painter = painterResource(id = R.drawable.logoapp),
                contentDescription = null,
                modifier = Modifier.size(250.dp),
            )
        }
    }
}

