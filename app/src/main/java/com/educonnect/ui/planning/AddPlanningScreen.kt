package com.educonnect.ui.planning

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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.educonnect.R
import com.educonnect.ui.components.CustomAdminAddButton
import com.educonnect.ui.components.CustomAdminAddPageTitleTextView
import com.educonnect.ui.theme.OnPrimaryOpacity
import com.educonnect.ui.theme.Primary
import com.educonnect.ui.theme.Secondary

@Composable
fun AddPlanningScreen(
    context: Context,
    onBackClick: () -> Unit,
    onAddClick: () -> Unit
) {
    var teacher by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var courseType by remember { mutableStateOf("") }
    var roomNumber by remember { mutableStateOf("") }
    var coursePath by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

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
            CustomTopAppBar(
                onHomeClick = onBackClick,
                onSearch = {},
                onProfileClick = {},
                onLogoutClick = {}
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomAdminAddPageTitleTextView(
                text = stringResource(R.string.ajout_d_un_planning),
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Form Container
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(OnPrimaryOpacity.copy(alpha = 0.2F))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PlanningTextField(value = teacher, label = "Enseignant(s)", onValueChange = { teacher = it })
                PlanningTextField(value = title, label = "Titre", onValueChange = { title = it })
                PlanningTextField(value = courseType, label = "Type du cours", onValueChange = { courseType = it })
                PlanningTextField(value = roomNumber, label = "N° Salle", onValueChange = { roomNumber = it })
                PlanningTextField(value = coursePath, label = "Parcours", onValueChange = { coursePath = it })

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    PlanningTextField(value = startTime, label = "Heure début", onValueChange = { startTime = it }, modifier = Modifier.weight(1f))
                    PlanningTextField(value = endTime, label = "Heure fin", onValueChange = { endTime = it }, modifier = Modifier.weight(1f))
                }

                PlanningTextField(value = date, label = "Date", onValueChange = { date = it })

                Spacer(modifier = Modifier.height(16.dp))

                CustomAdminAddButton(
                    onAddClick = onAddClick,
                    buttonText = stringResource(R.string.ajouter),
                )
            }
        }
    }
}

@Composable
fun PlanningTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Secondary) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            cursorColor = Secondary,
            focusedIndicatorColor = Secondary,
            unfocusedIndicatorColor = Secondary
        ),
        singleLine = true,
        modifier = modifier.fillMaxWidth()
    )
}
