package com.educonnect.ui.home

import CustomTopAppBar
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.educonnect.R
import com.educonnect.di.Injection
import com.educonnect.ui.components.CustomTextView
import com.educonnect.ui.components.CustomWelcomeCard
import com.educonnect.ui.theme.Primary
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.educonnect.ui.components.CustomAdminHomeAddButton
import com.educonnect.ui.navigation.Screen


@Composable
fun AdminHomeScreen(
    context: Context,
    navController: NavController,
    onLogout: () -> Unit
) {
    val viewModel: HomeViewModel = remember { Injection.provideHomeViewModel(context) }
    val adminData by viewModel.adminData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

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

        Image(
            painter = painterResource(id = R.drawable.logoapp),
            contentDescription = null,
            modifier = Modifier.size(300.dp)
                .align(Alignment.Center),
        )

        Box(

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Top AppBar
                CustomTopAppBar(
                    onHomeClick = { /* TODO: Handle Home Click */ },
                    onSearch = { /* TODO: Handle Search Click */ },
                    onProfileClick = { /* TODO: Handle Profile Click */ },
                    onLogoutClick = {
                        viewModel.clearSession()
                        onLogout()
                    }
                )

                Spacer(modifier = Modifier.height(40.dp))

                if (isLoading) {
                    CircularProgressIndicator(color = Primary)
                } else {
                    adminData?.let {
                        val fullName = "${it.nom} ${it.prenom}"
                        CustomWelcomeCard(fullName)
                    } ?: run {
                        Text(text = "Aucun administrateur trouvé", color = Color.Red)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /* TODO: Ajouter Gestion des utilisateurs */

                        navController.navigate(Screen.AddUser.route)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp)
                ) {
                    CustomTextView(text = stringResource(R.string.ajouter_un_utilisateur))
                }

                Button(
                    onClick = {
                        navController.navigate(Screen.AddPlanning.route)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp)
                ) {
                    CustomTextView(text = stringResource(R.string.ajouter_un_planning))
                }

                /** Ajout d'autre buttons **/

                CustomAdminHomeAddButton(
                    onClick = {
                        navController.navigate(Screen.AddCampus.route)
                    },
                    buttonText = stringResource(R.string.ajouter_un_campus)
                )

                CustomAdminHomeAddButton(
                    onClick = {
                        navController.navigate(Screen.AddBuilding.route)
                    },
                    buttonText = stringResource(R.string.ajouter_un_b_timent)
                )

                CustomAdminHomeAddButton(
                    onClick = {
                        navController.navigate(Screen.AddSalle.route)
                    },
                    buttonText = stringResource(R.string.ajouter_une_salle)
                )
            }
        }
    }
}


