package com.educonnect.ui.home.teacher.planning
import CustomTopAppBar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.educonnect.di.Injection
import com.educonnect.model.CoursUiModel
//import com.educonnect.model.sampleCours
import com.educonnect.ui.components.CustomAdminAddPageTitleTextView
import com.educonnect.ui.components.ScrollableFormLayout
import kotlinx.coroutines.flow.filter
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlanningScreen(
    onBackToHome: () -> Unit,
    onLogout: () -> Unit,
) {
    val viewModel = remember { Injection.provideTeacherPlanningViewModel() }
    val coursList = viewModel.coursList
    val isLoading = viewModel.isLoading

    val selectedDate = remember { mutableStateOf(LocalDate.now()) }

    LaunchedEffect(Unit) {
        viewModel.loadPlanningForTeacher(2L)
    }

    val filteredCours = coursList
        .filter { it.date == selectedDate.value }
        .mapIndexed { index, cours ->
            if (index == 0) cours.copy(isHighlighted = true) else cours
        }
    val currentWeek = remember(selectedDate.value) {
        val startOfWeek = selectedDate.value.with(java.time.DayOfWeek.MONDAY)
        (0..6).map { offset ->
            val date = startOfWeek.plusDays(offset.toLong())
            date
        }
    }


    ScrollableFormLayout(
        topContent = {
            CustomTopAppBar(
                onHomeClick = onBackToHome,
                onSearch = {},
                onProfileClick = {},
                onLogoutClick = onLogout,
            )
        }
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        CustomAdminAddPageTitleTextView("PLANNING")
        Spacer(modifier = Modifier.height(12.dp))

        // === Bandeau date ===
        Box(modifier = Modifier.fillMaxWidth()) {
            Column {
                // Ligne avec date et bouton refresh
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(selectedDate.value.dayOfMonth.toString(), fontSize = 28.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Column {
                            Text(
                                selectedDate.value.dayOfWeek.getDisplayName(
                                    TextStyle.SHORT,
                                    Locale.FRENCH
                                ),
                                fontSize = 14.sp
                            )
                            Text(
                                "${
                                    selectedDate.value.month.getDisplayName(
                                        TextStyle.FULL,
                                        Locale.FRENCH
                                    ).replaceFirstChar { it.uppercase() }
                                } ${selectedDate.value.year}",
                                fontSize = 14.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        if (selectedDate.value == LocalDate.now()) {
                            Box(
                                modifier = Modifier
                                    .background(Color(0xFFFFC107), shape = RoundedCornerShape(8.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text("Aujourd’hui", color = Color.White, fontSize = 14.sp)
                            }
                        }
                    }
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Rafraîchir",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable { viewModel.loadPlanningForTeacher(2L) }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Navigation jours
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("<<", color = Color.White, modifier = Modifier.clickable {
                        selectedDate.value = selectedDate.value.minusWeeks(1)
                    })

                    currentWeek.forEach { date ->
                        val selected = selectedDate.value == date
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .background(
                                    if (selected) Color(0xFFFFC107) else Color.Transparent,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { selectedDate.value = date }
                        ) {
                            Text(
                                text = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.FRENCH)
                                    .take(1), // D, L, M, ...
                                fontSize = 12.sp,
                                color = if (selected) Color.White else Color.Black
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = date.dayOfMonth.toString(),
                                fontSize = 14.sp,
                                color = if (selected) Color.White else Color.Black,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Text(">>", color = Color.White, modifier = Modifier.clickable {
                        selectedDate.value = selectedDate.value.plusWeeks(1)
                    })
                }

                Spacer(modifier = Modifier.height(6.dp))
                Divider(thickness = 1.dp, color = Color.LightGray)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            if (filteredCours.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Aucun cours pour ce jour.", color = Color.White)
                }
            } else {
                Column {
                    // En-tête colonnes
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Heure",
                            modifier = Modifier.width(90.dp),
                            fontSize = 14.sp,
                            color = Color.White
                        )
                        Text(
                            text = "Cours",
                            modifier = Modifier.padding(start = 8.dp),
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Liste des cours du jour
                    filteredCours.forEach { cours ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(
                                modifier = Modifier.width(90.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = cours.heureDebut, fontSize = 13.sp)
                                Text(text = "-", fontSize = 13.sp)
                                Text(text = cours.heureFin, fontSize = 13.sp)
                            }

                            Spacer(modifier = Modifier.width(8.dp))
                            PlanningCard(cours)
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}


@Composable
fun PlanningCard(cours: CoursUiModel) {
    val bgColor = if (cours.isHighlighted) Color(0xFFFFC107) else MaterialTheme.colorScheme.surface

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(cours.titre, style = MaterialTheme.typography.titleMedium)
            Text("CM - ${cours.salle}", style = MaterialTheme.typography.bodySmall)
            Text(cours.professeur, style = MaterialTheme.typography.bodySmall, color = Color.Red)

            cours.groupes.forEach {
                Text(it, style = MaterialTheme.typography.bodySmall)
            }

            Text(
                cours.description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.DarkGray
            )
        }
    }
}