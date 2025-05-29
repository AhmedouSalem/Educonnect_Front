package com.educonnect.ui.planning

import CustomTopAppBar
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.educonnect.R
import com.educonnect.di.Injection
import com.educonnect.ui.components.*
import com.educonnect.ui.theme.OnPrimaryOpacity
import com.educonnect.ui.theme.Primary
import com.educonnect.ui.theme.Secondary
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun AddPlanningScreen(
    context: Context,
    onBackClick: () -> Unit,
    viewModel: PlanningViewModel = remember { Injection.providePlanningViewModel(context = context) }
) {
    val mention by viewModel.mention.collectAsState()
    val parcours by viewModel.parcours.collectAsState()
    val cours by viewModel.cours.collectAsState()
    val typeCours by viewModel.typeCours.collectAsState()
    val campus by viewModel.campus.collectAsState()
    val batiment by viewModel.batiment.collectAsState()
    val salle by viewModel.salle.collectAsState()
    val heureDebut by viewModel.heureDebut.collectAsState()
    val heureFin by viewModel.heureFin.collectAsState()
    val date by viewModel.date.collectAsState()
    val message by viewModel.message.collectAsState()

    val mentionList by viewModel.mentionList.collectAsState()
    val parcoursList by viewModel.parcoursList.collectAsState()
    val coursList by viewModel.coursList.collectAsState()
    val campusList by viewModel.campusList.collectAsState()
    val batimentList by viewModel.batimentList.collectAsState()
    val salleList by viewModel.salleList.collectAsState()
    val typeCoursList = viewModel.typeCoursList

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Pickers
    val calendar = Calendar.getInstance()

    val scrollState = rememberScrollState()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Primary.copy(alpha = 0.1f))
    ) {
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

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .clip(RoundedCornerShape(16.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomAdminAddPageTitleTextView(text = "Ajout d’un planning")

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(OnPrimaryOpacity.copy(alpha = 0.2F))
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CustomDropdown("Mention", mentionList, mention, viewModel::onMentionSelected)

                    if (mention.isNotBlank()) {
                        CustomDropdownWithLabelValue(
                            label = "Parcours",
                            items = parcoursList,
                            selectedValue = parcours,
                            onItemSelected = viewModel::onParcoursSelected
                        )
                    }

                    if (parcours.isNotBlank()) {
                        CustomDropdownWithLabelValue(
                            label = "Cours",
                            items = coursList,
                            selectedValue = cours,
                            onItemSelected = viewModel::onCoursSelected
                        )
                    }


                    CustomDropdown("Type du cours", typeCoursList, typeCours, viewModel::onTypeCoursSelected)

                    CustomDropdown("Campus", campusList, campus, viewModel::onCampusSelected)

                    if (campus.isNotBlank()) {
                        CustomDropdown("Bâtiment", batimentList, batiment, viewModel::onBatimentSelected)
                    }

                    if (batiment.isNotBlank()) {
                        CustomDropdown("N° Salle", salleList, salle, viewModel::onSalleSelected)
                    }

                    // Heure début
                    CustomTimePicker(label = "Heure début", time = heureDebut) { viewModel.onHeureDebutChange(it) }
                    // Heur fin
                    CustomTimePicker(label = "Heure fin", time = heureFin) { viewModel.onHeureFinChange(it) }
                    // Date
                    CustomDatePicker(label = "Date", date = date) { viewModel.onDateChange(it) }

                    Spacer(modifier = Modifier.height(12.dp))

                    CustomAdminAddButton(
                        onAddClick = viewModel::ajouterPlanning,
                        buttonText = "AJOUTER"
                    )
                }
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
