package com.educonnect.ui.home.etudiant


import CustomTopAppBar
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
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
import com.educonnect.ui.components.CustomWelcomeCard
import com.educonnect.ui.theme.Primary
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.educonnect.ui.components.CustomAdminHomeAddButton
import com.educonnect.ui.home.TeacherHomeViewModel


@Composable
fun StudentHomeScreen(
    context: Context,
    navController: NavController,
    onLogout: () -> Unit
) {
    val viewModel: StudentHomeViewModel = remember { Injection.provideStudentHomeViewModel(context) }
    val studentData by viewModel.studentData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent)
    ) {

        Image(
            painter = painterResource(id = R.drawable.app_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Image(
            painter = painterResource(id = R.drawable.logoapp),
            contentDescription = null,
            modifier = Modifier.size(300.dp).align(Alignment.Center),
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTopAppBar(
                onHomeClick = {},
                onSearch = {},
                onProfileClick = {},
                onLogoutClick = {
                    viewModel.clearSession()
                    onLogout()
                }
            )

            Spacer(modifier = Modifier.height(40.dp))

            if (isLoading) {
                CircularProgressIndicator(color = Primary)
            } else {
                studentData?.let {
                    val fullName = "${it.nom} ${it.prenom}"
                    CustomWelcomeCard(fullName)
                } ?: run {
                    Text(text = "Aucun enseignant trouvé", color = Color.Red)
                }
            }


            Spacer(modifier = Modifier.height(16.dp))


            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically), // centrage et espacement

                horizontalAlignment = Alignment.CenterHorizontally

            ) {

                CustomAdminHomeAddButton(
                    onClick = {
                        //TODO VOIR MES COURS
                    },
                    buttonText = stringResource(R.string.mes_cours)
                )
                CustomAdminHomeAddButton(
                    onClick = {
                        //TODO VOIR MON PLANNING
                    },
                    buttonText = stringResource(R.string.mon_planning)
                )


            }


        }
    }
}


