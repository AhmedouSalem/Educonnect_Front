package com.educonnect.ui.campus

import CustomTopAppBar
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.educonnect.R
import com.educonnect.di.AppSession
import com.educonnect.di.Injection
import com.educonnect.ui.components.CustomAdminAddButton
import com.educonnect.ui.components.CustomAdminAddPageTitleTextView
import com.educonnect.ui.components.CustomAdminFormTextField
import com.educonnect.ui.components.ScrollableFormLayout
import kotlinx.coroutines.launch

@Composable
fun AddCampusScreen(
    context: Context,
    onLogout: () -> Unit,
    onBackClick: () -> Unit,
    onNavigateToCampusList: () -> Unit,
    viewModel: CampusViewModel = remember { Injection.provideCampusViewModel(context) }
) {
    val nom by viewModel.nom.collectAsState()
    val ville by viewModel.ville.collectAsState()
    val message by viewModel.message.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    ScrollableFormLayout(
        topContent = {
            CustomTopAppBar(
                onHomeClick = onBackClick,
                onSearch = { },
                onProfileClick = { },
                onLogoutClick = {
                    AppSession.sessionManager.clearUserData()
                    onLogout()
                }
            )
        }
    ) {

        //Voir liste des listes
        CustomAdminAddButton(
            buttonText = "Voir liste des listes",
            onAddClick = onNavigateToCampusList,
        )

        Spacer(modifier = Modifier.height(32.dp))

        CustomAdminAddPageTitleTextView(text = stringResource(R.string.ajout_d_un_campus))

        Spacer(modifier = Modifier.height(24.dp))
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
        // Snackbar affiché en bas
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(alignment = Alignment.End)
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


