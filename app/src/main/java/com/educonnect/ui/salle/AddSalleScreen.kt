package com.educonnect.ui.salle

import CustomTopAppBar
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.educonnect.R
import com.educonnect.di.Injection
import com.educonnect.ui.components.*
import com.educonnect.ui.theme.OnPrimaryOpacity
import com.educonnect.ui.theme.Primary
import kotlinx.coroutines.launch

@Composable
fun AddSalleScreen(
    context: Context,
    onBackClick: () -> Unit,
    viewModel: SalleViewModel = remember { Injection.provideSalleViewModel(context) }
) {
    val numero by viewModel.numero.collectAsState()
    val capacite by viewModel.capacite.collectAsState()
    val type by viewModel.type.collectAsState()
    val etage by viewModel.etage.collectAsState()
    val message by viewModel.message.collectAsState()
    val campus by viewModel.campus.collectAsState()
    val campusList by viewModel.campusList.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Primary.copy(alpha = 0.1f))
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

            CustomAdminAddPageTitleTextView(text = "Ajout d’une salle")

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(OnPrimaryOpacity.copy(alpha = 0.2F))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CustomAdminFormTextField(
                    value = numero,
                    label = stringResource(R.string.num_ro),
                    onValueChange = viewModel::onNumeroChange
                )

                CustomAdminFormTextField(
                    value = capacite,
                    label = stringResource(R.string.capacit),
                    onValueChange = viewModel::onCapaciteChange
                )

                CustomDropdown("Campus", campusList, campus, viewModel::onCampusSelected)

                if (campus.isNotBlank()) {
                    CustomDropdown(
                        label = stringResource(R.string.b_timent),
                        items = viewModel.batimentList.collectAsState().value,
                        selectedItem = viewModel.batimentCode.collectAsState().value,
                        onItemSelected = viewModel::onBatimentNomChange
                    )
                }

                CustomDropdown(
                    label = stringResource(R.string.type),
                    items = viewModel.typesDeSalles,
                    selectedItem = type,
                    onItemSelected = viewModel::onTypeChange
                )

                CustomDropdown(
                    label = stringResource(R.string.tage),
                    items = viewModel.etages,
                    selectedItem = etage,
                    onItemSelected = viewModel::onEtageChange
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomAdminAddButton(
                    onAddClick = viewModel::ajouterSalle,
                    buttonText = stringResource(R.string.ajouter)
                )
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

    message?.let {
        LaunchedEffect(it) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(it)
                viewModel.clearMessage()
            }
        }
    }
}
