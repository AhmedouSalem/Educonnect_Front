package com.educonnect.ui.building

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
import com.educonnect.ui.components.CustomDropdown
import com.educonnect.ui.components.ScrollableFormLayout
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBuildingScreen(
    context: Context,
    onLogout: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: BuildingViewModel = remember { Injection.provideBuildingViewModel(context) }
) {
    val code by viewModel.code.collectAsState()
    val anneC by viewModel.anneC.collectAsState()
    val campusNom by viewModel.campusNom.collectAsState()
    val campusList by viewModel.campusList.collectAsState()
    val message by viewModel.message.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var expanded by remember { mutableStateOf(false) }

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
            onAddClick = { /** navController.navigate(Screen.ListUsers.route) **/ },
        )

        Spacer(modifier = Modifier.height(32.dp))

        CustomAdminAddPageTitleTextView(stringResource(R.string.ajout_d_un_b_timent))
        Spacer(modifier = Modifier.height(24.dp))

        CustomAdminFormTextField(
            value = code,
            label = "Code",
            onValueChange = viewModel::onCodeChange
        )

        CustomAdminFormTextField(
            value = anneC,
            label = "Année de construction",
            onValueChange = viewModel::onAnneCChange
        )

        // Menu déroulant pour campus
        CustomDropdown(
            label = "Campus",
            items = campusList,
            selectedItem = campusNom,
            onItemSelected = viewModel::onCampusNomChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomAdminAddButton(
            onAddClick = {
                viewModel.ajouterBatiment();
            },
            buttonText = stringResource(R.string.ajouter),
        )
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.End)
        )
    }

    // Message affiché
    message?.let {
        LaunchedEffect(it) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(it)
                viewModel.clearMessage()
            }
        }
    }
}
