import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.educonnect.model.CoursUiModel
import com.educonnect.model.sampleCours
import com.educonnect.ui.components.CustomAdminAddPageTitleTextView
import com.educonnect.ui.components.ScrollableFormLayout
import com.educonnect.ui.theme.Secondary

@Composable
fun PlanningScreen() {
    val days = listOf(
        "D" to 21, "L" to 22, "M" to 23,
        "M" to 24, "J" to 25, "V" to 26, "S" to 27
    )
    val selectedDate = remember { mutableStateOf(24) }
    val horaires = listOf("11:35", "13:05", "13:15", "14:45", "15:10", "16:40")

    ScrollableFormLayout(
        topContent = {
            CustomTopAppBar(
                onHomeClick = {},
                onSearch = {},
                onProfileClick = {},
                onLogoutClick = {}
            )
        }
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        CustomAdminAddPageTitleTextView("PLANNING")
        Spacer(modifier = Modifier.height(12.dp))

        // Barre date + bouton refresh + semaine
        Box(modifier = Modifier.fillMaxWidth()) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("24", fontSize = 28.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Column {
                            Text("Mer", fontSize = 14.sp)
                            Text("Janvier 2025", fontSize = 14.sp)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .background(Color(0xFFFFC107), shape = RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text("Aujourd’hui", color = Color.White, fontSize = 14.sp)
                        }
                    }
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Rafraîchir",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable { /* refresh */ }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("<<", color = Color.White, modifier = Modifier.clickable { })
                    days.forEach { (abbr, day) ->
                        val selected = selectedDate.value == day
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .background(
                                    if (selected) Color(0xFFFFC107) else Color.Transparent,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { selectedDate.value = day }
                        ) {
                            Text(
                                text = abbr,
                                fontSize = 12.sp,
                                color = if (selected) Color.White else Color.Black,
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = day.toString(),
                                fontSize = 14.sp,
                                color = if (selected) Color.White else Color.Black,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }
                    Text(">>", color = Color.White, modifier = Modifier.clickable { })
                }

                Spacer(modifier = Modifier.height(6.dp))
                Divider(thickness = 1.dp, color = Color.LightGray)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 🕒 Heure + cours alignés
        Column {
            // Titre
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

            // Liste des cours
            sampleCours.forEach { cours ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    // Colonne Heure
                    Column(
                        modifier = Modifier.width(90.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = cours.heureDebut, fontSize = 13.sp)
                        Text(text = "-", fontSize = 13.sp)
                        Text(text = cours.heureFin, fontSize = 13.sp)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Colonne Cours
                    PlanningCard(cours)
                }

                Spacer(modifier = Modifier.height(4.dp))
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
