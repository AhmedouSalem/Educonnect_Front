package com.educonnect.ui.campus

import CustomTopAppBar
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.educonnect.R
import com.educonnect.di.Injection
import com.educonnect.ui.components.CustomAdminAddButton
import com.educonnect.ui.components.CustomAdminAddPageTitleTextView
import com.educonnect.ui.components.CustomAdminFormTextField
import com.educonnect.ui.theme.OnPrimaryOpacity
import kotlinx.coroutines.launch

@Composable
fun AddCampusScreen(
    context: Context,
    onBackClick: () -> Unit,
    viewModel: CampusViewModel = remember { Injection.provideCampusViewModel(context) }
) {
    val nom by viewModel.nom.collectAsState()
    val ville by viewModel.ville.collectAsState()
    val message by viewModel.message.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        // Background
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CustomTopAppBar(
                onHomeClick = onBackClick,
                onSearch = {},
                onProfileClick = {},
                onLogoutClick = {}
            )

            Spacer(modifier = Modifier.height(32.dp))

            CustomAdminAddPageTitleTextView(
                text = stringResource(R.string.ajout_d_un_campus),
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Form container
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(OnPrimaryOpacity.copy(alpha = 0.2F))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CustomAdminFormTextField(
                    value = nom,
                    label = "Nom",
                    onValueChange = viewModel::onNomChange
                )

                CustomAdminFormTextField(
                    value = ville,
                    label = "Ville",
                    onValueChange = viewModel::onVilleChange
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomAdminAddButton(
                    onAddClick = { viewModel.ajouterCampus() },
                    buttonText = stringResource(R.string.ajouter),
                )
            }
        }

        // Snackbar affiché en bas
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

    // Affiche le message s’il existe
    message?.let {
        LaunchedEffect(it) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(it)
                viewModel.clearMessage()
            }
        }
    }
}


